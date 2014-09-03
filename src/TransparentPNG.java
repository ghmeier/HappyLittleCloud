	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.EventQueue;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.LinearGradientPaint;
	import java.awt.Point;
	import java.awt.image.BufferedImage;
	import java.io.File;
	import java.io.IOException;
	import javax.imageio.ImageIO;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.UIManager;
	import javax.swing.UnsupportedLookAndFeelException;

	public class TransparentPNG {

	    public static void main(String[] args) {
	        new TransparentPNG("hey");
	    }

	    public TransparentPNG(String fileName) {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (ClassNotFoundException e){
	                	
	                	
	                }catch (InstantiationException e){
	                	
	                }catch( IllegalAccessException e){
	                
	                }catch (UnsupportedLookAndFeelException e) {

	                }

	                JFrame frame = new JFrame("Testing");
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.setLayout(new BorderLayout());
	                frame.add(new TestPane());
	                frame.pack();
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
	            }
	        });
	    }

	    public class TestPane extends JPanel {

	        private BufferedImage img;

	        public TestPane() {
	            try {
	                img = ImageIO.read(new File("spermy.PNG"));
	                
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }

	        @Override
	        public Dimension getPreferredSize() {
	            return img == null ? new Dimension(0, 50) : new Dimension(img.getWidth(), img.getHeight());
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g.create();
	            /*LinearGradientPaint lgp = new LinearGradientPaint(
	                    new Point(0, 0),
	                    new Point(0, getHeight()),
	                    new float[]{0f, 1f},
	                    new Color[]{Color.GREEN, Color.YELLOW});*/
	            //g2d.setPaint(lgp);
	            //g2d.fillRect(0, 0, getWidth(), getHeight());
	            if (img != null) {
	                int x = (getWidth() - img.getWidth()) / 2;
	                int y = (getHeight() - img.getHeight()) / 2;
	                g2d.drawImage(img, x, y, this);
	            }
	            g2d.dispose();
	        }
	    }
	
}
