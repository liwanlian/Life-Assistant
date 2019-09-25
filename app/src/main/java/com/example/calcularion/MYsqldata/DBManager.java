package com.example.calcularion.MYsqldata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2019/9/14.
 */
//对数据库的数据进行操作的类
public class DBManager {
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase db;
    private String table_regester="sql_regester";

    private String table_data="sql_data";

    private String table_total="sql_total";

    public DBManager(Context context){
        myDatabaseHelper=new MyDatabaseHelper(context);
        db=myDatabaseHelper.getWritableDatabase();
    }
    //插入数据的数据 合适的id
    public  int countid(){
        return db.query(table_regester, null, null, null, null, null, null).getCount()+1;
    }
  //添加新的用户数据
    public void addnewdata(int id,String name,long acc_num,String password){
//       db.beginTransaction();//开始事务
//        try{
//            ContentValues cv=new ContentValues();
//            cv.put("id",id);
//            cv.put("name",name);
//            cv.put("acc_num",acc_num);
//            cv.put("password",password);
//            db.insert(table_regester,"id",cv);
//           db.setTransactionSuccessful();
//        }finally {
//            db.endTransaction();
//        }
       // db.beginTransaction();//开始事务
        ContentValues cv=new ContentValues();
        cv.put("id",id);
        cv.put("name",name);
        cv.put("acc_num",acc_num);
        cv.put("password",password);
        db.insert(table_regester,"id",cv);
//      db.endTransaction();
    }
//查找账号  即用户注册时用的手机号，一个手机号只能注册一次
    public boolean searchacc_num(long acc_num){
    //    db.beginTransaction();
        boolean result=false;
//        try {
//            Cursor c =db.rawQuery("select * from "+table_regester,null);
//            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
//                if (c.getLong(2)==acc_num){
//                   result=true;
//                   break;
//                }
//            }
//            c.close();
//        }finally {
//            db.setTransactionSuccessful();
        Cursor c =db.rawQuery("select * from "+table_regester,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(2)==acc_num){
                result=true;
                break;
            }
        }
        c.close();
       // db.endTransaction();
        return result;
       // }
    }
    //忘记密码的操作1  查找数据库中是否存在对应的用户名和账号  找得到返回对应的id号
    public int searchaccandname(String name,long acc_num){
        //db.beginTransaction();

        int result=countid();
//        try {
//            Cursor c =db.rawQuery("select * from "+table_regester,null);
//            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
//                if (c.getLong(2)==acc_num && c.getString(1).equals(name)){
//                    result=c.getInt(0);
//                    break;
//                }
//            }
//            c.close();
//        }finally {
        Cursor c =db.rawQuery("select * from "+table_regester,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(2)==acc_num && c.getString(1).equals(name)){
                result=c.getInt(0);
                break;
            }
        }
        c.close();
   //       db.endTransaction();
            return result;
       // }

    }
    //忘记密码的操作2，更新用户的密码
    public boolean updatapassword(int id,String password){
   //     db.beginTransaction();
        boolean result=false;
//        try {
//            ContentValues cv=new ContentValues();
//            cv.put("password",password);
//            db.update(table_regester,cv,"id="+id,null);
//        }finally {
//            db.setTransactionSuccessful();
//            return result;
//        }
        ContentValues cv=new ContentValues();
        cv.put("password",password);
        db.update(table_regester,cv,"id="+id,null);  //更新回到注册表

        result=true;
      //  db.endTransaction();
        return  result;
    }
    //改账号
    public  boolean updataacc(int id,long accnum){
       boolean result=false;
        ContentValues cv=new ContentValues();
        cv.put("acc_num",accnum);
        db.update(table_regester,cv,"id="+id,null);

        result=true;
        //  db.endTransaction();
       return result;
    }
    //该用户名
    public  boolean updateusername(int id,String username){
        boolean result=false;
        ContentValues cv=new ContentValues();
        cv.put("name",username);
        db.update(table_regester,cv,"id="+id,null);
        result=true;
        //  db.endTransaction();
        return result;
    }
    //登录的时候，查看账号与密码是否一致
    public boolean searchaccandpassword(long acc_num,String password){
        boolean result=false;
        Cursor c =db.rawQuery("select * from "+table_regester,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(2)==acc_num && c.getString(3).equals(password)){
                result=true;
                break;
            }
        }
        return  result;
    }

    /*
    针对某用户下的记账数据
    table_data
     */
    //返回表的合适id
    public int countuid(){
        return db.query(table_data, null, null, null, null, null, null).getCount()+1;
    }
    //给用户的数据表添加数据
    public boolean addusrData(int id,long acc_num,String type,double money,String date ,String timedetail,String remark){
        boolean result=false;
        ContentValues cv=new ContentValues();
       cv.put("id",id);
       cv.put("acc_num",acc_num);
       cv.put("type",type);
       cv.put("money",money);
       cv.put("date",date);
        cv.put("timedetail",timedetail);
       cv.put("remark",remark);
       db.insert(table_data,"id",cv);
       result=true;
       return  result;
    }
    //根据日期  账号 返回相应的记账信息列表
    public List<Map<String,Object>>  searchlistdata(long acc_num,String date,int type){
        List<Map<String ,Object>> listdata = new ArrayList<Map<String, Object>>();
        Cursor c =db.rawQuery("select * from "+table_data,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
               if (type==0){
                   if (c.getLong(1)==acc_num && c.getString(4).equals(date)){
                       Map<String, Object> listItem=new HashMap<String,Object>();
                       listItem.put("title",c.getString(2)+" "+String.valueOf(c.getDouble(3)));
                       listItem.put("date",c.getString(5));
                       listItem.put("remark",c.getString(6));
                       System.out.println("item="+listItem);
                       listdata.add(listItem);
                   }
               }
               else if (type==1){
                   if (c.getLong(1)==acc_num && c.getString(4).equals(date) && c.getString(2).equals("收入")){
                       Map<String, Object> listItem=new HashMap<String,Object>();
                       listItem.put("title",c.getString(2)+" "+String.valueOf(c.getDouble(3)));
                       listItem.put("date",c.getString(5));
                       listItem.put("remark",c.getString(6));
                       listdata.add(listItem);
                   }

               }
               else if (type==2){
                   if (c.getLong(1)==acc_num && c.getString(4).equals(date) && c.getString(2).equals("支出")){
                       Map<String, Object> listItem=new HashMap<String,Object>();
                       listItem.put("title",c.getString(2)+" "+String.valueOf(c.getDouble(3)));
                       listItem.put("date",c.getString(5));
                       listItem.put("remark",c.getString(6));
                       listdata.add(listItem);
                   }

               }

        }
        return  listdata;
    }
    //根据日期  账号 返回相应的记账 id 数组
    public int[] searchiddata(int flag,long acc_num,String date){
        int[] iddata;
        int size=0;
        Cursor c =db.rawQuery("select * from "+table_data,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (flag==0){
                if (c.getLong(1)==acc_num && c.getString(4).equals(date)){
                    size++;
                }
            }
            else if (flag == 1) {
                if (c.getLong(1)==acc_num && c.getString(4).equals(date) && c.getString(2).equals("收入")){
                    size++;
                }

            }
            else{
                if (c.getLong(1)==acc_num && c.getString(4).equals(date) && c.getString(2).equals("支出")){
                    size++;
                }
            }
        }
        iddata=new int[size];
        int i=0;
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (flag==0){
                if (c.getLong(1)==acc_num && c.getString(4).equals(date)){
                   iddata[i]=c.getInt(0);
                   i++;
                }
            }
            else if (flag == 1) {
                if (c.getLong(1)==acc_num && c.getString(4).equals(date) && c.getString(2).equals("收入")){
                    iddata[i]=c.getInt(0);
                    i++;
                }

            }
            else{
                if (c.getLong(1)==acc_num && c.getString(4).equals(date) && c.getString(2).equals("支出")){
                    iddata[i]=c.getInt(0);
                    i++;
                }
            }
        }
        return iddata;
    }
    //根据id 返回该id对应的数据条
    public Bundle searchbundblebyid(int id){
        Bundle bundle=new Bundle();
        Cursor c =db.rawQuery("select * from "+table_data,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getInt(0)==id){
                bundle.putInt("id",c.getInt(0));
                bundle.putLong("acc_num",c.getLong(1));
                bundle.putString("type",c.getString(2));
                bundle.putDouble("money",c.getDouble(3));
                bundle.putString("date",c.getString(4));
                bundle.putString("timedetail",c.getString(5));
                bundle.putString("remark",c.getString(6));
                break;
            }
        }
        return  bundle;
    }
    //根据id 更新记账的数据
    public boolean updatejizhang(int id,double money,String remark){
        boolean result=false;
        ContentValues cv=new ContentValues();
        cv.put("money",money);
        cv.put("remark",remark);
        db.update(table_data,cv,"id="+id,null);
        result=true;
        return result;
    }
    //根据账号 找到分表中是该id的数量
    public int searchidsizeind(long acc){
        int count=0;
        Cursor c=db.rawQuery("select * from "+table_data,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc)
                count++;
        }
        return count;
    }
    //根据账号  找到分表中相应的id，返回id数组
    public  int[] searchidarrind(long acc_num){
        int size=searchidsizeint(acc_num);
        int[] idarr=new int[size];
        int i=0;
        Cursor c=db.rawQuery("select * from "+table_data,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num){
                idarr[i]=c.getInt(0);
                i++;
            }
        }
        return idarr;
    }
    //根据 id 新的账号 更新分表中对应的账号
    public boolean updatezInd(int id,long acc_num){
        boolean result=false;
        ContentValues cv=new ContentValues();
        cv.put("acc_num",acc_num);
        db.update(table_data,cv,"id="+id,null);
        return result;
    }
    //总表的操作
    //添加数据
    public boolean adddataintotal(int id,long acc_num,String date,String type,double lingchen ,double morning,double afternoon,double night,double sumtotal){
        boolean result=false;
        ContentValues cv=new ContentValues();
        cv.put("id",id);
        cv.put("acc_num",acc_num);
        cv.put("date",date);
        cv.put("type",type);
        cv.put("ningchen",lingchen);
        cv.put("morning",morning);
        cv.put("afternoon",afternoon);
        cv.put("night",night);
        cv.put("sumtotal",sumtotal);
        db.insert(table_total,"id",cv);
        result=true;
        return result;
    }
    //给总表添加数据的时候，找到合适的id
    public  int searchid(){
        return db.query(table_total, null, null, null, null, null, null).getCount()+1;
    }
    // 判断根据日期 账号是否找到对应的id
    public String judgeproperid(long acc_num,String date,String type){
        String result="no";
        Cursor c =db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num && c.getString(2).equals(date)&&c.getString(3).equals(type)){
                result="yes";
                break;
            }
        }
        return  result;
    }
    public String judgeproper(long acc_num,String date){
        String result="no";
        Cursor c =db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num && c.getString(2).equals(date)){
                result="yes";
                break;
            }
        }
        return  result;
    }
    //根据 日期和账号 类型 找到合适的id 返回id
    public int searchproperid(String type,long acc_num,String date){
        int id=0;
        Cursor c =db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num && c.getString(2).equals(date)&& c.getString(3).equals(type)){
               id=c.getInt(0);
                break;
            }
        }
        return  id;
    }
    //根据账号 找到总表中是该id的数量
    public int searchidsizeint(long acc){
        int count=0;
        Cursor c=db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc)
                count++;
        }
        return count;
    }
    //根据账号  找到总表中相应的id，返回id数组
    public  int[] searchidarr(long acc_num){
        int size=searchidsizeint(acc_num);
        int[] idarr=new int[size];
        int i=0;
        Cursor c=db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num){
                idarr[i]=c.getInt(0);
                i++;
            }
        }
        return idarr;
    }
    //根据 id 新的账号 更新总表中对应的账号
    public boolean updatezInt(int id,long acc_num){
        boolean result=false;
        ContentValues cv=new ContentValues();
        cv.put("acc_num",acc_num);
        db.update(table_total,cv,"id="+id,null);
        return result;
    }
    //更新总表的数据
    public boolean updatetotaldata(int flag,int id,double data){
        boolean result=false;
        double countdata=0;
        double toatal=0;
        String[] arrtime=new String[]{"ningchen","morning","afternoon","night"};
        //根据flag 找到对应时段的记账数据
        Cursor c =db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getInt(0)==id){
                countdata=c.getDouble(flag+3);
                toatal=c.getDouble(8);
                break;
            }
        }
        countdata=countdata+data;
        toatal=toatal+data;
        ContentValues cv=new ContentValues();
        cv.put("sumtotal",toatal);
        cv.put(arrtime[flag-1],countdata);
        db.update(table_total,cv,"id="+id,null);
        result=true;
        return result;
    }
    //编辑记账数据  将数据更新回到总表
    public  boolean updatetotaldatatwo(int flag,long acc_num,String date,String type,double zhong,double newdata){
        boolean result=false;
        String[] arrtime=new String[]{"ningchen","morning","afternoon","night"};
        double zhongjian=0;
        double zhongjian1=0;
        //根据flag 找到对应时段的记账数据
        Cursor c =db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num && c.getString(2).equals(date) && c.getString(3).equals(type)){
                zhongjian=c.getDouble(3+flag);
                zhongjian1=c.getDouble(8);
                break;
            }
        }
        zhongjian=zhongjian-zhong+newdata;
        zhongjian1=zhongjian1-zhong+newdata;
        ContentValues cv=new ContentValues();
        cv.put("sumtotal",zhongjian1);
        cv.put(arrtime[flag-1],zhongjian);
        int id=searchproperid(type,acc_num,date);
        db.update(table_total,cv,"id="+id,null);
        result=true;
        return result;
    }
    //根据账号 日期 返回当天的记账记录
    public int[] searchcurrentdata(int id){
        int[] currentdata=new int[4];
        Cursor c =db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getInt(0)==id){
                currentdata[0]=(int)c.getDouble(4);
                currentdata[1]=(int)c.getDouble(5);
                currentdata[2]=(int)c.getDouble(6);
                currentdata[3]=(int)c.getDouble(7);
            }
        }
        return  currentdata;
    }
//返回总支出 或者 总收入
    public double inoutdata(long acc_num,String date,String type){
        double result=0;
        Cursor c =db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num && c.getString(2).equals(date)&& c.getString(3).equals(type))
            {
                result=c.getDouble(8);
                break;
            }
        }
        return result;
    }
    //根据 id 账号 记账类型 查出数据库池中的记账数目 以id为界，返回id之前，满足条件的数目
    public int searchcountone(int id,long acc_num,String type){
        int counts=0;
        Cursor c =db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num && c.getString(3).equals(type))
            {
                counts++;
               if (c.getInt(0)==id)
                   break;
            }
        }
        return counts;
    }

    //根据日期 账号 找到该天近几天的记账记录返回
    public float[] searchdatedataone(int id,long acc_num,String type){
        float[] datedata;
        int size;
        size=searchcountone(id,acc_num,type);
        if (size<7){
            datedata=new float[size] ;
            int y= 0;
            Cursor c =db.rawQuery("select * from "+table_total,null);
            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                if (c.getLong(1) == acc_num && c.getString(3).equals(type)) {
                    datedata[y]=(float) c.getDouble(8);
                    y++;
                    if (c.getInt(0)==id)
                        break;
                }
            }
        }
        else{
            datedata=new float[7];
            int y= 0;
            int[] countsid=new int[size];
            Cursor c =db.rawQuery("select * from "+table_total,null);
            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                if (c.getLong(1) == acc_num && c.getString(3).equals(type)) {
                   countsid[y]=c.getInt(0);
                   y++;
                   if (c.getInt(0)==id)
                       break;
                }
            }
            Cursor c1 =db.rawQuery("select * from "+table_total,null);
            int j=7;
            int i=0;
            for (c1.moveToFirst();!c1.isAfterLast();c1.moveToNext()){
                if (c1.getInt(0)==countsid[size-j]){
                    datedata[i]=(float) c1.getDouble(8);
                    i++;
                    j--;
                    if (j==0)
                        break;
                }
            }
        }
        return datedata;
    }
    //根据账号  日期 查看table_data的数据池中 ，记账的数目
    public int  searchsizeindata(long acc_num,String date,String type){
        int result=0;
        Cursor c =db.rawQuery("select * from "+table_data,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num && c.getString(4).equals(date) && c.getString(2).equals(type))
                result++;
        }
        return result;
    }
    //查看记录的界面   单折线图的x轴数据
    public String[] searchcheckx(long acc_num,String date,String type){
        int size=searchsizeindata(acc_num,date,type);
        String[] checkx=new String[size];
        int i=0;//timedetail
        Cursor c =db.rawQuery("select * from "+table_data,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num && c.getString(4).equals(date) && c.getString(2).equals(type)){
                checkx[i]=c.getString(5);
                i++;
            }
        }
        return checkx;
    }
    //查看记录的界面  y轴的数据
    public float[] searchchecky(long acc_num,String date,String type){
        int size=searchsizeindata(acc_num,date,type);
        float[] checky=new float[size];
        int i=0;
        Cursor c =db.rawQuery("select * from "+table_data,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getLong(1)==acc_num && c.getString(4).equals(date) && c.getString(2).equals(type)){
                checky[i]=(float)c.getDouble(3);
                i++;
            }
        }
        return checky;
    }
    //根据 账号 日期（eg：2019年9） 记账类型 返回总额
    public double searchzongw(long acc,String date,String type){
        double result=0;
        Cursor c =db.rawQuery("select * from "+table_total,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            String str=c.getString(2);
            if (c.getLong(1)==acc && str.substring(0,str.indexOf("月")).equals(date)&& c.getString(3).equals(type)){
                result=result+c.getDouble(8);
            }
        }
        return result;
    }
    public void closedb(){
        db.close();
    }
}
