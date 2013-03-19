/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioreceiver;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Mixer.Info;
/**
 *
 * @author Thom
 */
public class AudioReceiver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            int BUFFER_SIZE = 2048;
            ServerSocket server = new ServerSocket(2001, 10);
            Socket conn = server.accept();
            ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
            
            AudioFormat format = new AudioFormat(44100.0F, 16, 2, true, true); 
     
            Info[] lines = AudioSystem.getMixerInfo();
            
            SourceDataLine outputLine = null; 
            DataLine.Info outInfo = new DataLine.Info(SourceDataLine.class, format);
            try
            {
                outputLine = (SourceDataLine) AudioSystem.getMixer(lines[0]).getLine(outInfo); 
                outputLine.open(format, BUFFER_SIZE); 
                outputLine.start(); 
                byte[] b2 = new byte[outputLine.getBufferSize()/2];
                int i =0;
                while (i < 50) 
                { 	    	
                    in.read(b2);
                    outputLine.write(b2,0,b2.length);
                    System.out.println("Read: ");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AudioReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
