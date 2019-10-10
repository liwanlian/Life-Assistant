package com.example.calcularion.Userdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcularion.R;
import com.example.calcularion.exitsystem;

public class CalActivity extends exitsystem {
    Button b1,b2,b3,b4;//1,2,3,4
    Button b5,b6,b7,b8;//5,6,7,8
    Button b9,b10,b11,b12;//9,*,+,=
    Button b13,b14,b15,b0;//  /,-,.,0
    Button exit;//退出
    Button clear;//清除
    TextView see;//显示框 显示数字和运算符
    TextView seeresult;//显示运算的结果

    double[] num={0,0,0,0,0,0,0,0,0,0,0,0};//用户输入的运算数存放的位置    12位
    int[] fuhao={0,0,0,0,0,0,0,0,0,0,0,0};//用户输入的运算符  12位

    int count1=0,count2=0;//count1记录的是用户输入的运算数的个数count2记录的是输入的运算符的个数
    int zheng=0,xiao=0,jilu=1;//zheng是运算数整数部分，xiao是运算数小数部分,jilu是记录一个运算数按下的小数的位数
    double shu=0;//存放运算数时的中间变量
    int flagd=0;//按下小数点的标志，按下的时候为1，否则就为0
    int flagn=0;//按下运算数的标志，按下的时候为1，否则就为0
    int flagdeng=0;//按下等号的标志，按下为1，否则为0
    int flagling=0;//判断按下的0是否为首位
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);
        function_control();
    }
    private void function_control(){
        see=(TextView)findViewById(R.id.TextView);//显示框 显示运算数和运算符
        seeresult=(TextView)findViewById(R.id.TextView1);//显示运算的结果

        exit=(Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=getIntent().getBundleExtra("bundle");
                Intent intent=new Intent(CalActivity.this,HomeActivity.class);
                intent.putExtra("bundle",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });//退出计算器

        clear=(Button)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bclear();
            }
        });//清除按键
        b0=(Button)findViewById(R.id.button0);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext,str;
                int f=0;
                str=see.getText().toString();
                if (flagn==0){
                    if (str.equals("0")){
                        flagn=1;
                        flagling=1;
                        // judgechu();
                    }
                    else{
                        flagn=1;
                        flagling=1;
                        see.setText(str+"0");
                        judgechu();
                    }
                }
                else{
                    if (flagling==1){
                        see.setText(str);
                    }
                    else{
                        buttontext= ((Button) v).getText().toString();
                        jianhuanum(flagdeng,buttontext);
                        dealwith(f);
                    }
                }
            }
        });//按下数字0
        b1=(Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext;
                buttontext= ((Button) v).getText().toString();
                jianhuanum(flagdeng,buttontext);
                int f=1;
                dealwith(f);
            }
        });//按下1
        b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext;
                buttontext= ((Button) v).getText().toString();
                jianhuanum(flagdeng,buttontext);
                int f=2;
                dealwith(f);
            }
        });//按下2
        b3=(Button)findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext;
                buttontext= ((Button) v).getText().toString();
                jianhuanum(flagdeng,buttontext);
                int f=3;
                dealwith(f);
            }
        });//按下3
        b4=(Button)findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext;
                buttontext= ((Button) v).getText().toString();
                jianhuanum(flagdeng,buttontext);
                int f=4;
                dealwith(f);
            }
        });//按下4
        b5=(Button)findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext;
                buttontext= ((Button) v).getText().toString();
                jianhuanum(flagdeng,buttontext);
                int f=5;
                dealwith(f);
            }
        });//按下5
        b6=(Button)findViewById(R.id.button6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext;
                buttontext= ((Button) v).getText().toString();
                jianhuanum(flagdeng,buttontext);
                int f=6;
                dealwith(f);
            }
        });//按下6
        b7=(Button)findViewById(R.id.button7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext;
                buttontext= ((Button) v).getText().toString();
                jianhuanum(flagdeng,buttontext);
                int f=7;
                dealwith(f);
            }
        });//按下7
        b8=(Button)findViewById(R.id.button8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext;
                buttontext= ((Button) v).getText().toString();
                jianhuanum(flagdeng,buttontext);
                int f=8;
                dealwith(f);
            }
        });//按下8
        b9=(Button)findViewById(R.id.button9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttontext;
                buttontext= ((Button) v).getText().toString();
                jianhuanum(flagdeng,buttontext);
                int f=9;
                dealwith(f);
            }
        });//按下数字9

        b10=(Button)findViewById(R.id.button10);
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str,strm;
                int w=3;
                str=see.getText().toString();
                strm=str.substring(0,str.length()-1);
                if (str!=null)
                {
                    if (flagn==1){
                        if (flagd==0)
                            see.setText(str + "*");
                        else{
                            if(xiao==0){
                                see.setText(str +"0"+ "*");
                            }
                            else{
                                see.setText(str + "*");
                            }
                        }
                    }
                    else
                    {
                        see.setText(strm+"0"+"*");
                    }
                }
                else {
                    see.setText("0" + "*");
                }
                chulif(w);
            }
        });//按下 *
        b11=(Button)findViewById(R.id.button11);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w=1;
                String str,strm;
                str=see.getText().toString();
                strm=str.substring(0,str.length()-1);
                if (str!=null)
                {
//                    if (flagn==1)
//                    see.setText(str + "+");
//                    else
//                        see.setText(strm+"+");
                    if (flagn==1){
                        if (flagd==0)
                            see.setText(str + "+");
                        else{
                            if(xiao==0){
                                see.setText(str +"0"+ "+");
                            }
                            else{
                                see.setText(str + "+");
                            }
                        }
                    }

                    else
                    {
                        see.setText(strm+"0"+"+");
                    }
                }
                else {
                    //  see.setText("0" + "+");
                }
                chulif(w);
            }
        });//按下 +
        b12=(Button)findViewById(R.id.button12);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagdeng=1;
                String result;
                int i=count2,y,z=0,w=0,m=0;
                int count3=0;
                int flag=1;
                if (count2==0){
                    shu=(double)xiao*1.0/jilu;
                    shu=shu+zheng;
                    num[0]=shu;
                    result = String.valueOf(num[0]);//将一个double转成string
                    //   see.setText(result);
                    seeresult.setText(result);
                }
                else{
                    while (i!=0){
                        if (fuhao[i-1]==3||fuhao[i-1]==4){
                            i--;
                            count3++;
                        }
                        else{
                            i--;
                        }
                    }
                    if (count3==0){
                        shu=(double)xiao*1.0/jilu;
                        shu=shu+zheng;
                        num[count1]=shu;
                        count1++;
                        y=count1;
                        while (y!=2){
                            zongyuan(fuhao[z],num[z],num[z+1],z);
                            y=count1;
                        }
                        //跳出循环，只剩下两个数的运算
                        zongyuan(fuhao[0],num[0],num[1],0);
                        result = String.valueOf(num[0]);//将一个double转成string
                        //see.setText(result);
                        seeresult.setText(result);
                    }//只有加法或者减法
                    else{
                        if (count3==count1){
                            shu=(double)xiao*1.0/jilu;
                            shu=shu+zheng;
                            num[count1]=shu;
                            count1++;
                            y=count1;
                            while (y!=2){
                                zongyuan(fuhao[z],num[z],num[z+1],z);
                                y=count1;
                            }
                            //跳出循环，只剩下两个数的运算
                            zongyuan(fuhao[0],num[0],num[1],0);
                            result = String.valueOf(num[0]);//将一个double转成string
                            see.setText(result);
                        }//只有乘法或者除法
                        else{
                            shu=(double)xiao*1.0/jilu;
                            shu=shu+zheng;
                            num[count1]=shu;
                            count1++;
                            while (w!=count2-1){
                                if (fuhao[w]==3||fuhao[w]==4){
                                    zongyuan(fuhao[w],num[w],num[w+1],w);
                                    w=0;
                                }
                                else{
                                    w++;
                                }
                            }//先将乘除部分先做了
                            m=count1;
                            while (m!=2){
                                zongyuan(fuhao[0],num[0],num[1],0);
                                m=count1;
                            }//将加减部分处理
                            //跳出循环，只剩下两个数的运算
                            zongyuan(fuhao[0],num[0],num[1],0);
                            result = String.valueOf(num[0]);//将一个double转成string
                            seeresult.setText(result);
                            // see.setText(result);
                        }//加减乘除混合在一起运算
                    }
                }
            }
        });//按下 =
        b13=(Button)findViewById(R.id.button13);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w=4;
                String str,strm;
                str=see.getText().toString();
                strm=str.substring(0,str.length()-1);
                if (str!=null)
                {
                    if (flagn==1){
                        if (flagd==0)
                            see.setText(str + "/");
                        else{
                            if(xiao==0){
                                see.setText(str +"0"+ "/");
                            }
                            else{
                                see.setText(str + "/");
                            }
                        }
                    }

                    else
                    {
                        see.setText(strm+"0"+"/");
                    }
                }
                else {
                    //  see.setText("0" + "/");
                }
                chulif(w);
            }
        });//按下 /
        b14=(Button) findViewById(R.id.button14);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w=2;
                String str,strm;
                str=see.getText().toString();
                strm=str.substring(0,str.length()-1);//TextUtils.isEmpty(edit.getText())
                if (str!=null){
                    if (flagn==1){
                        if (flagd==0)
                            see.setText(str + "-");
                        else{
                            if(xiao==0){
                                see.setText(str +"0"+ "-");
                            }
                            else{
                                see.setText(str + "-");
                            }
                        }
                    }
                    else
                    {
                        see.setText(strm+"0"+"-");
                    }
                }
                else {
                    //see.setText("0-");
                }
                chulif(w);
            }
        });//按下 -
        b15=(Button)findViewById(R.id.button15);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str;
                if(flagd==0){
                    flagd=1;
                    if (flagdeng==1)
                    {
                        bclear();
                        see.setText("0" + ".");
                        flagn=1;
                    }
                    else{
                        str=see.getText().toString();
                        if (zheng==0){
                            if (flagling!=1)
                            {
                                if (!str.equals("0"))
                                {
                                    see.setText(str + "0"+".");
                                    flagn=1;
                                }
                                else
                                {
                                    see.setText(str+".");
                                    flagn=1;
                                }
                            }
                            else
                            {
                                see.setText(str +".");
                                flagling=0;
                            }
                        }
                        else{
                            see.setText(str+ ".");
                        }
                    }
                }
                else{

                }
            }
        });//按下小数点
    }
    public void  xclear(){
        zheng=0;
        xiao=0;
        flagd=0;
        flagn=0;
        shu=0;
        jilu=1;
        flagling=0;
    }//小清除函数
    public void jianhua(int k3){
        int i=k3;
        while (i!=count1-1){
            num[i+1]=num[i+2];
            fuhao[i]=fuhao[i+1];
            i++;
        }
        count1--;
        count2--;
    }//封装简化函数

    public void jianhuanum(int j,String buttontext){
        String str,strm;
        if (j==1)
            bclear();
        else{ }
        str=see.getText().toString();
        strm=str.substring(0,str.length()-1);
        if (str.equals("0"))
            see.setText( buttontext);
        else{
            if (flagling==1)
            {
                see.setText(strm + buttontext);
                flagling=0;
            }
            else
                see.setText(str + buttontext);
        }
    }//显示数字部分的简化函数    1--9

    public void zongyuan(int k,double k1,double k2,int k3){
        if (k==1){
            num[k3]=k1+k2;
            jianhua(k3);
        }//加法运算
        if(k==2){
            num[k3]=k1-k2;
            jianhua(k3);
        }//减法运算
        if (k==3){
            num[k3]=k1*k2;
            jianhua(k3);
        }//乘法运算
        if (k==4){
            num[k3]=k1/k2;
            jianhua(k3);
        }//除法运算
    }//y运算函数

    public void dealwith(int k){
        flagn=1;
        if (flagd==0){
            zheng=zheng*10+k;
        }
        else{
            xiao=xiao*10+k;
            jilu=jilu*10;
        }
    }//处理用户按下的数字键

    public  void chulin(){
        shu=(double)xiao*1.0/jilu;
        shu=shu+zheng;
        num[count1]=shu;
        count1++;
        xclear();
    }//将用户按下的运算数放进 num数组

    public void chulif(int k){
        if (count1==count2){
            if (count1==0){
                chulin();
                fuhao[count2]=k;
                count2++;
            }
            else{
                if (flagn==0){
                    fuhao[count2-1]=k;
                }
                else{
                    chulin();
                    fuhao[count2]=k;
                    count2++;
                }
            }
        }
    }//处理用户按下的运算符
    public void bclear(){
        see.setText("0");
        seeresult.setText("");
        double[] num={0,0,0,0,0,0,0,0,0,0,0,0};//用户输入的运算数存放的位置    12位
        int[] fuhao={0,0,0,0,0,0,0,0,0,0,0,0};//用户输入的运算符  12位
        zheng=0;
        xiao=0;
        flagd=0;
        flagn=0;
        shu=0;
        count1=0;
        count2=0;
        jilu=1;
        flagdeng=0;
        flagling=0;
    }//大清除
    public void judgechu(){
        if (count2==0){
            if(fuhao[count2]==4){
                Toast.makeText(CalActivity.this,"除数不能为0",Toast.LENGTH_LONG).show();
                bclear();
            }
        }
        else{
            if(fuhao[count2-1]==4){
                Toast.makeText(CalActivity.this,"除数不能为0",Toast.LENGTH_LONG).show();
                bclear();
            }
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
