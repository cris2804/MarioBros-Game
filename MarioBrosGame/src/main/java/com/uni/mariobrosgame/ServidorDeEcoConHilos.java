package com.uni.mariobrosgame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorDeEcoConHilos implements Runnable{
    private int nrocli = 5;
    private ManejadorDeConHilos[] cli = new ManejadorDeConHilos[nrocli];        
    private String[][] tablero = new String[5 + 1][8 + 1];
    
    public ServidorDeEcoConHilos() {                
        for(int i= 1; i <= 5; i++){
            for(int j= 1; j <= 8; j++){
                tablero[i][j] = ".";
            }
        }
    }
    
    public void run(){
        try {
            int i = 1;
            ServerSocket s = new ServerSocket(8189);
            while (true) {                
                Socket entrante = s.accept();
                System.out.println("Engendrado -> " + i);
                //Runnable 
                cli[i] = new ManejadorDeConHilos(entrante , i, tablero);
                Thread t = new Thread(cli[i]);
                t.start();
                i++;                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}