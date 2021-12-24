package com.example.demo;

import java.nio.charset.Charset;
import java.util.Scanner;

public class HammingCode {
    public static void main(String[] args) {
        int[] originalBytes =getBytes();
        int size = originalBytes.length;
        int[] wordCode=wordCode(originalBytes);
        int[] rValues = getRValue(originalBytes.length,wordCode,generatorMatrix(originalBytes.length));
        int[] fullWordKey = fullWordCode(wordCode,rValues);
        int [][] generatorMatrix = generatorMatrix(originalBytes.length);

        //##########################

        System.out.print("sent Bytes is  : " );
        printMatrix(originalBytes);
        System.out.print("R Values " ) ;
        printMatrix(rValues);
        System.out.println("the code Word is : ") ;
        printMatrix(fullWordKey);
        System.out.println("generator Matrix is : ");
        printMatrix(generatorMatrix);

        //####################################

        int [] sBytes = getBytes();
        if(sBytes.length==fullWordKey.length){
            int [] sFullWordCode = sBytes;
            int [] sValues = getRValue(sBytes.length,sFullWordCode,generatorMatrix);
            int decimal = Integer.parseInt(matrixToString(sValues),2);
            if(decimal!=0){
                int [] fixedWordCodeMatrix=fixTheError(sFullWordCode,decimal);
                System.out.println();
                System.out.println("error found !!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println();
                System.out.println("received Bytes is  : " );
                printMatrix(sBytes);
                System.out.println("the code Word is : " ) ;
                printMatrix(fullWordKey);
                System.out.println("generator Matrix is : ");
                printMatrix(generatorMatrix);
                System.out.println("you have error in position number "+decimal);
                System.out.println("the code Word after fixing is : ");
                printMatrix(fixedWordCodeMatrix);
                System.out.println("received Bytes after fixing  : ");
                printMatrix(codeWordToMessage(fixedWordCodeMatrix,size));
            }else {
                System.out.println("there is no error");
            }
        }else {
            System.out.println("wrong Syntax");
        }
    }

    public static int rCounter(int size){
        int r =0;
        while (Math.pow(2,r)<=size+r){
            double q = Math.pow(2,r);
            r++;
        }
        return r;
    }
    public static int[] getBytes(){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter bytes ");
        String s = sc.nextLine();
        int [] arr = new int[s.length()];
        for (int i = 0 ;i < s.length(); i++){
            arr[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }
        return arr;
    }
    public static void printMatrix(int [][] arr){
        for(int i =0 ; i < arr.length ;i++){
            for(int j =0 ; j < arr[0].length;j++){
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }
    public static void printMatrix(int [] arr){
        for(int i =0 ; i < arr.length ;i++){
                System.out.print(arr[i]);
        }
        System.out.println();
    }
    public static int[] wordCode(int[] originalBytes){
        int[] arr = new int [originalBytes.length+rCounter(originalBytes.length)];
        int c=0;
        for (int i = 0 ; i < arr.length;i++){
            if(i+1!=Math.pow(2,i-c)){
                arr[i]=originalBytes[c];
                c++;
            }
        }
        return arr;
    }
    public static int[][] generatorMatrix(int size) {
        int row = rCounter(size);
        int[][] arr = new int[row][row + size];
        for (int col = 0; col < size + row; col++) {
            int c = 0;
            String s = Integer.toBinaryString(col + 1);
            int a = s.length() - 1;
            while (c < row) {
                if (a >= 0) {
                    arr[a][col] = Integer.parseInt(String.valueOf(s.charAt(c)));
                    a--;
                }
                c++;
            }
        }
        return arr;
    }
    public static int[] getRValue(int size,int[] wordCode ,int[][]gArr){
        int[] arr = new int [rCounter(size)];
        for (int i = 0 ; i<gArr.length;i++){
            int s =0;
            for (int  j =0 ;j <gArr[0].length;j++){
                s+=gArr[i][j]*wordCode[j];
            }
            arr[i]=s%2;
        }
        return arr;
    }
    public static int[] fullWordCode(int []wordCode,int []rValues){
        int []arr = new int [wordCode.length];
        int c=0;
        int x =0;
        for (int i = 0 ; i < arr.length;i++){
            if(i+1 == Math.pow(2,i-c)){
                arr[i]=rValues[x];
                x++;
            }else{
                arr[i]=wordCode[i];
                c++;
            }
        }
        return arr;
    }
    public static String matrixToString(int [] arr){
        String s ="";
        for (int i = arr.length-1;i>= 0;i--){
            s+=arr[i];
        }
        return s;
    }
    public static int [] fixTheError(int [] arr1 ,int pos){
        pos =pos-1;
        int[] arr = new int [arr1.length];
        for (int i =0;i<arr.length;i++){
            arr[i]=arr1[i];
        }
        if(arr[pos]==0){
            arr[pos]=1;
        }else {
            arr[pos]=0;
        }
        return arr;
    }
    public static int [] codeWordToMessage(int[] arr1,int size){
        int[] arr =new int[arr1.length-rCounter(size)];
        int c = 0;
        int x = 0;
        for (int i = 0 ; i < arr1.length;i++){
            if(i+1 != Math.pow(2,i-c)){
                arr[c]=arr1[i];
                c++;
            }
        }
        return arr;
    }
}
