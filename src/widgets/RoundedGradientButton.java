
package widgets;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class RoundedGradientButton extends JButton {
    
        private Color startColor = Color.decode("#4c7de6");
        private Color endColor = Color.decode("#9d5be6");

        public RoundedGradientButton(String text, String iconPath) {
            super(text);
            setOpaque(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 15));

            // Load and set icon if provided
            if (iconPath != null) {
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
                Image tintedImage = tintImage(originalIcon.getImage());
                setIcon(new ImageIcon(tintedImage));
                setHorizontalTextPosition(SwingConstants.CENTER);
                setVerticalTextPosition(SwingConstants.BOTTOM);
                setIconTextGap(10);
            }
        }

        private Image tintImage(Image image) {
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(image, 0, 0, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));  // 80% opacity white
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, image.getWidth(null), image.getHeight(null));

            g2d.dispose();
            return bufferedImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradient = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
            g2.setPaint(gradient);

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);

            g2.dispose();
        }

    }