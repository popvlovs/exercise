package com.syt;

/**
 * Created by Think on 2017/7/18.
 */
import java.io.*;

public class Arihmetic {
    private char ch;
    private char read()throws IOException{
        return (char)System.in.read();
    }
    private double expression()throws IOException{
        double t=term();
        while(true){
            switch(ch){
                case '+':t+=term();break;
                case '-':t-=term();break;
                case ')':return t;
                case '\n':return t;
                default:throw new IOException("syntex error");
            }
        }
    }
    private double term()throws IOException{
        double f=factor();
        while(true){
            switch(ch){
                case '*':f*=factor();break;
                case '/':f/=factor();break;
                default:return f;
            }
        }
    }
    private double factor()throws IOException{
        double var,minus=1.0;
        //读入一个符号
        ch=read();
        //处理正负号
        while(ch=='+'||ch=='-'){
            if(ch=='-')
                minus*=-1.0;
            ch=read();
        }
        //读数字
        if(Character.isDigit(ch)){
            var=ch-'0';
            ch=read();
            while(Character.isDigit(ch)){
                double t=ch-'0';
                var=var*10+t;
                ch=read();
            }
        }
        //括号表达式
        else if(ch=='('){
            var=expression();
            if(ch==')')
                ch=read();
            else throw new IOException("syntex error");
        }
        else throw new IOException("syntex error");
        return minus*var;
    }
    public double readStatement()throws IOException{
        return expression();
    }
    public static void main(String[] args)throws IOException{
        Arihmetic s=new Arihmetic();
        System.out.println(s.readStatement());
    }
}