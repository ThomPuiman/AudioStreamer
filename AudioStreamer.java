package audiostreamer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Mixer.Info;
import java.net.*;
import java.io.*;

class AudioStreamer {
    AudioFormat format;
    int BUFFER_SIZE = 2048;
    
    public AudioStreamer() throws LineUnavailableException {
        try {
			format = new AudioFormat(44100.0F, 16, 2, true, true); 
            Info[] lines = AudioSystem.getMixerInfo();
            for (int i = 0; i < lines.length; i++)
                System.out.println(i+": "+lines[i].getName()+"\n"+lines[i].getDescription());

            javax.sound.sampled.Port.Info portInfo = new javax.sound.sampled.Port.Info(TargetDataLine.class,
            javax.sound.sampled.Port.Info.SPEAKER.getName(), false);
            DataLine.Info dataLineInfo = new
            DataLine.Info(portInfo.getLineClass(),
            format);
            javax.sound.sampled.Mixer mixer = AudioSystem.getMixer(lines[2]);
            
            Thread.sleep(200);

            
            TargetDataLine inputLine;
            inputLine = (TargetDataLine)mixer.getLine(dataLineInfo);
            inputLine.open(format);
            System.out.println(inputLine.getBufferSize() / 5);
            inputLine.start(); 
            //outputLine.start(); 
            int i = 0 ;
            byte[] buffer = new byte[inputLine.getBufferSize() / 5]; 
            //java.io.File fileNew = new java.io.File("C:\\Users\\Thom\\Documents\\test.wav");
            //javax.sound.sampled.AudioFileFormat.Type type = javax.sound.sampled.AudioFileFormat.Type.WAVE;
            Socket req = new Socket("192.168.0.101", 2001);
            //Socket req = new Socket("localhost", 2001);
            ObjectOutputStream out = new ObjectOutputStream(req.getOutputStream());
            while (i < 50) 
            { 	    	
                
                inputLine.read(buffer,0,buffer.length);
                out.write(buffer);
                //i++;
                //int bytesWritten = outputLine.write(buffer,0, bytesRead);
                //System.out.println("Read: " + bytesRead + " Written: " + bytesWritten);
            }
            
//            AudioSystem.write(null, type, fileNew);
        } catch (InterruptedException ex) {
            Logger.getLogger(AudioStreamer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AudioStreamer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        try
        {
            new AudioStreamer();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
}