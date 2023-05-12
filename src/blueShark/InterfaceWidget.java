package blueShark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class InterfaceWidget {
    static int curPos = 0;
    public int pos = -1;
    public int size;
    public JLabel countOfChosen = new JLabel("0");
    public JLabel countOfChosenForInfo = new JLabel("0");
    ImageIcon imgIcon;
    JLabel imageLabel;
    JLabel textLabel;
    int price;
    String description = "";
    JLabel priceLabel;
    JLabel degreesLabel;
    JPanel widget = new JPanel();
    DrinkEnumType type = null;
    public JButton minusButton = new JButton("-");
    public JButton plusButton = new JButton("+");
    public final MouseListener iconChoose = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            curPos = pos;
            widget.setCursor(new Cursor(Cursor.HAND_CURSOR));
            if (priceLabel == null) {
                Image image = imgIcon.getImage();
                Image newImg = image.getScaledInstance(size + 2,
                        size + 2, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(newImg));
            } else {
                priceLabel.setForeground(Color.decode("#00CC99"));
                if (type == null)
                    return;
                if (type.isAlcoholic())
                    degreesLabel.setForeground(Color.decode("#CC3333"));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (priceLabel == null) {
                Image image = imgIcon.getImage();
                Image newImg = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(newImg));
            } else {
                priceLabel.setForeground(Color.decode("#f1ce65"));
                if (type == null)
                    return;
                if (type.isAlcoholic())
                    degreesLabel.setForeground(Color.decode("#fFF3333"));
            }
        }
    };

    InterfaceWidget(String path, String titleText, int newSize, JPanel panel) {
        widget.setLayout(new BoxLayout(widget, BoxLayout.Y_AXIS));
        imgIcon = new ImageIcon(path);
        imageLabel = new JLabel(imgIcon);
        if (imgIcon.getIconWidth() != newSize) {
            Image image = imgIcon.getImage();
            Image newImg = image.getScaledInstance(newSize, newSize, Image.SCALE_SMOOTH);
            imgIcon = new ImageIcon(newImg);
            imageLabel.setIcon(imgIcon);
        }
        size = imgIcon.getIconWidth();
        widget.add(imageLabel);
        widget.addMouseListener(iconChoose);
        if (panel != null)
            panel.add(widget);
        if (titleText.isEmpty())
            return;
        textLabel = new JLabel(titleText);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Times new roman", Font.ITALIC, 30));
        widget.add(textLabel);
    }

    InterfaceWidget(String path, String titleText, int price, int position,
                    int newSize, DrinkEnumType type, JPanel panel) {
        widget.setLayout(new BoxLayout(widget, BoxLayout.Y_AXIS));
        imgIcon = new ImageIcon(path);
        imageLabel = new JLabel(imgIcon);
        if (imgIcon.getIconWidth() != newSize) {
            Image image = imgIcon.getImage();
            Image newImg = image.getScaledInstance(newSize, newSize, Image.SCALE_SMOOTH);
            imgIcon = new ImageIcon(newImg);
            imageLabel.setIcon(imgIcon);
        }
        size = imgIcon.getIconWidth();
        widget.add(imageLabel);
        widget.addMouseListener(iconChoose);
        if (panel != null)
            panel.add(widget);
        if (titleText.isEmpty())
            return;
        textLabel = new JLabel(titleText);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Times new roman", Font.ITALIC, 30));
        widget.add(textLabel);
        if (price == 0)
            return;
        this.price = price;
        priceLabel = new JLabel(price + "$");
        priceLabel.setFont(new Font("Price", Font.BOLD, 30));
        priceLabel.setForeground(Color.decode("#f1ce65"));
        JPanel infoPan = new JPanel();
        infoPan.setLayout(new BoxLayout(infoPan, BoxLayout.X_AXIS));
        if (type != null && type.isAlcoholic()) {
            degreesLabel = new JLabel(type.getDegrees() + "°");
            degreesLabel.setFont(new Font("Degrees", Font.BOLD, 30));
            degreesLabel.setForeground(Color.decode("#FF3333"));
            infoPan.add(degreesLabel);
        }
        infoPan.add(Box.createHorizontalGlue());
        infoPan.add(priceLabel);
        widget.add(infoPan);
        minusButton.addMouseListener(iconChoose);
        minusButton.addMouseListener(iconChoose);
        JPanel choosePanel = new JPanel();
        choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.X_AXIS));
        minusButton.setBackground(Color.decode("#b5d3d8"));
        choosePanel.add(minusButton);
        countOfChosen.setHorizontalAlignment(SwingConstants.CENTER);
        countOfChosen.setFont(new Font("count", Font.BOLD, 20));
        choosePanel.add(Box.createHorizontalGlue());
        choosePanel.add(countOfChosen);
        choosePanel.add(Box.createHorizontalGlue());
        choosePanel.add(plusButton);
        plusButton.setBackground(Color.decode("#b5d3d8"));
        widget.add(choosePanel);
        if (position < 0)
            return;
        this.pos = position;
        widget.setBorder(BorderFactory.createLineBorder(Color.decode("#eeeeee"), 10));
        if (type == null)
            return;
        this.type = type;
    }
}
