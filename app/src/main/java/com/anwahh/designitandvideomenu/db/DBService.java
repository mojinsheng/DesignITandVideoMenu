package com.anwahh.designitandvideomenu.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBService {

    // 打开数据库对象
    private Connection conn = null;
    // 操作整合sql语句的对象
    private PreparedStatement ps = null;
    // 查询结果的集合
    private ResultSet rs = null;

    // DBService对象
    public static DBService dbService = null;

    /**
     * 构造方法 私有化
     */
    private DBService() {

    }

    public static DBService getDbService() {
        if (dbService == null) {
            dbService = new DBService();
        }
        return dbService;
    }

    /**
     * 获取数据库对象的状态   查
     * @return
     */
    public List<ShowStatus> getShowStatusData() {
        // 结果存放集合
        List<ShowStatus> list =new ArrayList<ShowStatus>();
        String sql = "select status from ShowStatus";
        conn = DBOpenHelper.getConn();
        try {
            if (conn != null && (!conn.isClosed())) {
                ps = conn.prepareStatement(sql);
                if (ps != null) {
                    rs = ps.executeQuery();
                    if (rs != null) {
                        while(rs.next()) {
                            ShowStatus s = new ShowStatus();
                            s.setStatus(rs.getString("status"));
                            list.add(s);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.clossAll(conn, ps, rs);  // 关闭相关操作
        return list;
    }

    /**
     * 获取数据库对象的状态   查
     * @return
     */
    public ShowStatus getShowStatusDataByStatus(String name) {
        String sql = "select status from ShowStatus where name=?";
        conn = DBOpenHelper.getConn();
        try {
            boolean closed=conn.isClosed();
            if((conn!=null)&&(!closed)){
                ps = conn.prepareStatement(sql);
                if (ps != null) {
                    rs = ps.executeQuery();
                    while(rs.next()) {
                        ShowStatus s = new ShowStatus();
                        s.setStatus(rs.getString("status"));
                        return s;
                    }
                }
            }
        } catch (SQLException e) {
                e.printStackTrace();
            }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return null;
    }

    /**
     * 修改数据库中对象的状态   改
     * */

    public int updateUserData(String status, String name){
        int result = -1;
        conn = DBOpenHelper.getConn();
        //MySQL 语句
        String sql="update ShowStatus set status=? where name=?";
        try {
            if(conn!=null){
                ps = conn.prepareStatement(sql);
                ps.setString(1,status);//第一个参数state 一定要和上面SQL语句字段顺序一致
                ps.setString(2,name);//第二个参数 name 一定要和上面SQL语句字段顺序一致
                result = ps.executeUpdate();//返回1 执行成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }
}
