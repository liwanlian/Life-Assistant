package com.example.calcularion.UserControl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcularion.Configure.PhoneEditText;
import com.example.calcularion.MYsqldata.DBManager;
import com.example.calcularion.R;
import com.example.calcularion.Userdata.UserActivity;
import com.example.calcularion.exitsystem;

public class Edit_accnumlast extends exitsystem implements View.OnClickListener {

    //界面控件
    TextView tv_back;
    PhoneEditText et_acc,et_zacc;
    Button  bt_sure;
    private long accnum;
    private int id;
    private DBManager db;
    private long oldacc;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_accnumlast);
        db=new DBManager(this);
        Bundle bundle=getIntent().getBundleExtra("bundle");
        accnum=bundle.getLong("acc_num");
        id=bundle.getInt("id");
        initView();
    }
    private void initView(){
        tv_back=(TextView)findViewById(R.id.editacclast_back);
        et_acc=(PhoneEditText)findViewById(R.id.editacclast_newacc);
        et_zacc=(PhoneEditText)findViewById(R.id.editacclast_zacc);
        bt_sure=(Button)findViewById(R.id.editacclast_sure);


        tv_back.setOnClickListener(this);
        bt_sure.setOnClickListener(this);
//
    }

    @Override
    public void onClick(View view) {
        Bundle bundle=getIntent().getBundleExtra("bundle");
        switch (view.getId()){
            case R.id.editacclast_back:
                Intent intent=new Intent(Edit_accnumlast.this,Edit_accnum.class);
                intent.putExtra("bundle",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.editacclast_sure:
                String acc=et_acc.getText().toString().replaceAll(" " ,"") ;
                String zacc=et_zacc.getText().toString().replaceAll(" " ,"") ;
                Toast.makeText(Edit_accnumlast.this,zacc,Toast.LENGTH_LONG).show();
                if (acc.equals(zacc)) {
                    if (Long.valueOf(acc)==accnum){
                        Toast.makeText(Edit_accnumlast.this,"当前输入的新账号与原始账号一致",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(Edit_accnumlast.this,UserActivity.class);
                        intent2.putExtra("bundle",bundle);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                    }
                    else{
                        boolean result=db.searchacc_num(Long.valueOf(acc));
                        if (!result)
                        {
                            boolean rr=db.updataacc(id,Long.valueOf(acc));
                            int size1=db.searchidsizeind(accnum);
                            int[] idsD=new int[size1];
                            idsD=db.searchidarrind(accnum);
                            for (int i=0;i<size1;i++){
                                db.updatezInd(idsD[i],Long.valueOf(acc));
                            }//更新分表对应的数据

                            int size2=db.searchidsizeint(accnum);
                            int[] idsT=new int[size2];
                            idsT=db.searchidarr(accnum);
                            for (int j=0;j<size2;j++){
                                db.updatezInt(idsT[j],Long.valueOf(acc));
                            }

                            System.out.println("id="+id+" "+"acc="+acc);
                            if (rr)
                            {
                                Bundle bundle1=new Bundle();
                                bundle1.putLong("acc_num",Long.valueOf(acc));
                                Toast.makeText(Edit_accnumlast.this,"修改账号成功",Toast.LENGTH_LONG).show();
                                Intent intent1=new Intent(Edit_accnumlast.this,UserActivity.class);
                                intent1.putExtra("bundle",bundle1);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                            }
                            else
                            {
                                Toast.makeText(Edit_accnumlast.this,"修改账号失败",Toast.LENGTH_LONG).show();
                                Intent intent1=new Intent(Edit_accnumlast.this,UserActivity.class);
                                intent1.putExtra("bundle",bundle);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                            }
                        }
                        else
                        {
                            Toast.makeText(Edit_accnumlast.this,"当前账号已被注册，请重新输入",Toast.LENGTH_LONG).show();
                            et_acc.setText("");
                            et_zacc.setText("");
                        }
                    }

                }
                else{
                    Toast.makeText(Edit_accnumlast.this,"输入的账号名不一致",Toast.LENGTH_LONG).show();
                    et_zacc.setText("");
                    et_acc.setText("");
                }
                break;
            default: break;
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
            System.exit(0);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else{
            Intent intent = new Intent();
            intent.setAction(exitsystem.SYSTEM_EXIT);
            sendBroadcast(intent);
        }
    }
}
