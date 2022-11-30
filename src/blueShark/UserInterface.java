package blueShark;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

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
            }
            else {
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
            }
            else {
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
            degreesLabel = new JLabel(type.getDegrees()+"°");
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

public class  UserInterface extends JFrame {
    public static final String PATH_CONST = "src/blueShark/images/";
    private final InterfaceWidget[] allFoodWidgets = new InterfaceWidget[36];
    private final HashMap<Integer, InterfaceWidget> chosenWidgets = new HashMap<>();
    int widgetIter = 0;
    public InternetOrder iOrder;
    private final JScrollPane scrollPane = new JScrollPane();
    private final Color frameColor = Color.decode("#b5d3d8");
    private JLabel returnLabel;
    private JLabel exitLabel;
    private final JLabel categoryLabel = new JLabel("Category: Standard");
    private final JLabel orderCount = new JLabel("Items count: 0");
    private final JLabel orderPriceCount = new JLabel("Price: 0$");
    public JFrame frame = new JFrame("Main page");
    private final JPanel centerPanel = new JPanel();
    private final JPanel northPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final JPanel payPanel = new JPanel();
    private final Font topFont = new Font("Times new roman", Font.BOLD, 24);
    MouseListener chooseItems = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            centerPanel.removeAll();
            chooseItems();
        }
    };
    MouseListener chooseDishes = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            chooseDishes();
        }
    };
    MouseListener chooseAlcohol = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            chooseAlco();
        }
    };
    MouseListener chooseSoft = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            chooseSoft();
        }
    };
    MouseListener backToMain = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            centerPanel.removeAll();
            frame.remove(scrollPane);
            payPanel.removeAll();
            frame.remove(payPanel);
            northPanel.removeAll();
            chooseType();
            frame.repaint();
        }

        @Override
            public void mouseEntered(MouseEvent e) {
            if (returnLabel != null)
                returnLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    };
    MouseListener foodAdded = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            InterfaceWidget curWidget = allFoodWidgets[InterfaceWidget.curPos];
            if (curWidget.pos < 16) {
                Dish dish = new Dish(curWidget.textLabel.getText(), "a", curWidget.price);
                iOrder.add(dish);
            } else {
                Drink drink = new Drink(curWidget.textLabel.getText(), "a", curWidget.type,
                        curWidget.price);
                if (curWidget.pos < 28) {
                    categoryLabel.setText("Category: Adult");
                    categoryLabel.setForeground(Color.decode("#333999"));
                }
                iOrder.add(drink);
            }
            chosenWidgets.put(curWidget.pos, curWidget);
            curWidget.countOfChosen.setText(String.valueOf(Integer.parseInt(curWidget.countOfChosen.getText())+1));
            curWidget.countOfChosenForInfo.setText(curWidget.countOfChosen.getText());
            orderCount.setText("Items count: " + iOrder.itemsQuantity());
            orderPriceCount.setText("Price: " + iOrder.costTotal() + "$");
        }
    };
    MouseListener foodRemoved = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            InterfaceWidget curWidget = allFoodWidgets[InterfaceWidget.curPos];
            if (Integer.parseInt(curWidget.countOfChosen.getText()) < 1)
                return;
            if (curWidget.countOfChosen.getText().equals("1"))
                chosenWidgets.remove(InterfaceWidget.curPos);
            iOrder.remove(curWidget.textLabel.getText());
            curWidget.countOfChosen.setText(String.valueOf(Integer.parseInt(curWidget.countOfChosen.getText())-1));
            curWidget.countOfChosenForInfo.setText(curWidget.countOfChosen.getText());
            orderCount.setText("Items count: " + iOrder.itemsQuantity());
            orderPriceCount.setText("Price: " + iOrder.costTotal() + "$");
            if (categoryLabel.getText().equals("Category: Standard"))
                return;
            boolean flag = true;
            for (int key : chosenWidgets.keySet()) {
                if (allFoodWidgets[key].type == null)
                    continue;
                if (allFoodWidgets[key].type.isAlcoholic()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                categoryLabel.setText("Category: Standard");
                categoryLabel.setForeground(Color.decode("#339999"));
            }
        }
    };

    MouseListener orderListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            myOrder();
        }
    };
    MouseListener endOrder = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            String[] options = {"Yes", "No"};
            int x = JOptionPane.showOptionDialog(frame, "Are you sure?", "Alert",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon(PATH_CONST + "cooking.png"), options, options[0]);
            if (x != 0)
                return;
            for (int key : chosenWidgets.keySet()) {
                InterfaceWidget widget = chosenWidgets.get(key);
                widget.countOfChosen.setText("0");
                widget.countOfChosenForInfo.setText("0");
            }
            chosenWidgets.clear();
            for (MenuItem order : iOrder.getItems()) {
                iOrder.remove(order);
            }
            northPanel.removeAll();
            centerPanel.removeAll();
            frame.remove(payPanel);
            orderCount.setText("Items count: " + iOrder.itemsQuantity());
            orderPriceCount.setText("Price: " + iOrder.costTotal() + "$");
            categoryLabel.setText("Category: Standard");
            categoryLabel.setForeground(Color.decode("#339999"));
            end();
        }
    };
    MouseListener infoListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            centerPanel.removeAll();
            showInfo();
        }
    };

    public UserInterface() {
        iOrder = new InternetOrder();
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == 27)
                    frame.dispose();
            }
        });
        buildWidgets();
        frame.setVisible(true);
        chooseType();
    }

    public void buildWidgets() {
        String[] args = new String[]
                {"food/bibimbap", "food/tteok", "food/kimchi", "food/ramen", "food/tom yam",
                        "food/salad", "food/green curry", "food/massaman", "food/halo halo",
                        "food/masala dosa", "food/pasta", "food/rolls", "food/sushi",
                        "food/shaved ice", "food/takoyaki", "food/noodle"};
        int[] values = new int[]{10, 11, 7, 9, 8, 5, 7, 7, 11, 6, 5, 10, 9, 4, 7, 4};
        String[] descriptions = new String[]{
                "korean cuisine dish", "korean cuisine dish", "korean cuisine dish", "japanese cuisine dish",
                "thailand cuisine dish", "international cuisine dish", "thailand cuisine dish", "thailand cuisine dish",
                "philippines cuisine dish", "indian cuisine dish", "italian cuisine dish", "japanese cuisine dish",
                "japanese cuisine dish", "hawaiian cuisine dish", "japanese cuisine dish", "chinese cuisine dish"};
        createWidgetCategory(args, values, descriptions);
        args = new String[]
                {"alcohol/vodka", "alcohol/black russian", "alcohol/wine", "alcohol/champagne",
                        "alcohol/bloody mary", "alcohol/beer", "alcohol/rum", "alcohol/whiskey",
                        "alcohol/manhattan", "alcohol/mojito", "alcohol/pina colada", "alcohol/blue lagoon"};
        values = new int[]{8, 6, 6, 5, 7, 7, 8, 10, 8, 6, 7, 7};
        createWidgetCategory(args, values);
        args = new String[]
                {"soft/cola", "soft/tea", "soft/coffee", "soft/water",
                        "soft/pelican", "soft/grenadine", "soft/orange juice", "soft/milkshake"};
        values = new int[]{5, 4, 6, 3, 8, 8, 6, 6};
        descriptions = new String[]
                {"Carbonated soft cold drink", "Chinese soft warmed drink",
                        "soft warm drink prepared from roasted coffee beans", "glass of water", "soft banana cocktail",
                        "soft pomegranate juice", "soft juice from fresh oranges", "desert milk and ice cream drink"};
        createWidgetCategory(args, values, descriptions);
        southPanel.setLayout(new GridLayout(1, 4));
        Font northFont = new Font("North", Font.BOLD, 32);
        orderCount.setFont(northFont);
        orderCount.setHorizontalAlignment(SwingConstants.CENTER);
        orderCount.setForeground(Color.decode("#339999"));
        orderPriceCount.setFont(northFont);
        orderPriceCount.setHorizontalAlignment(SwingConstants.CENTER);
        orderPriceCount.setForeground(Color.decode("#339999"));
        categoryLabel.setFont(northFont);
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setForeground(Color.decode("#339999"));
        southPanel.add(categoryLabel);
        southPanel.add(orderCount);
        southPanel.add(orderPriceCount);
        JButton myOrder = new JButton("Your order");
        myOrder.setFont(northFont);
        myOrder.setForeground(Color.decode("#339999"));
        myOrder.setBackground(Color.decode("#D7E8EC"));
        myOrder.setBorder(BorderFactory.createLineBorder(frameColor));
        myOrder.addMouseListener(orderListener);
        southPanel.add(myOrder);
        southPanel.setBackground(frameColor);
        returnLabel = new JLabel(new ImageIcon(PATH_CONST + "left-arrow.png"));
        returnLabel.addMouseListener(backToMain);
    }

    public void createWidgetCategory(String[] args, int[] values) {
        int i = 0;
        for (String arg : args) {
            String name = arg.substring(arg.indexOf("/") + 1);
            allFoodWidgets[widgetIter] = new InterfaceWidget(PATH_CONST + arg + ".png",
                    name, values[i], widgetIter, 192, getType(name), null);
            allFoodWidgets[widgetIter].widget.addMouseListener(infoListener);
            allFoodWidgets[widgetIter].plusButton.addMouseListener(foodAdded);
            allFoodWidgets[widgetIter].minusButton.addMouseListener(foodRemoved);
            widgetIter++;
            i++;
        }
    }

    public void createWidgetCategory(String[] args, int[] values, String[] descriptions) {
        int i = 0;
        for (String arg : args) {
            String name = arg.substring(arg.indexOf("/") + 1);
            allFoodWidgets[widgetIter] = new InterfaceWidget(PATH_CONST + arg + ".png",
                    name, values[i], widgetIter, 192, getType(name), null);
            allFoodWidgets[widgetIter].description = descriptions[i];
            allFoodWidgets[widgetIter].widget.addMouseListener(infoListener);
            allFoodWidgets[widgetIter].plusButton.addMouseListener(foodAdded);
            allFoodWidgets[widgetIter].minusButton.addMouseListener(foodRemoved);
            widgetIter++;
            i++;
        }
    }

    public DrinkEnumType getType(String arg) {
        return switch (arg) {
            case "vodka" -> DrinkEnumType.VODKA;
            case "black russian" -> DrinkEnumType.BLACK_RUSSIAN;
            case "wine" -> DrinkEnumType.WINE;
            case "champagne" -> DrinkEnumType.CHAMPAGNE;
            case "bloody mary" -> DrinkEnumType.BLOODY_MARY;
            case "beer" -> DrinkEnumType.BEER;
            case "rum" -> DrinkEnumType.RUM;
            case "whiskey" -> DrinkEnumType.WHISKEY;
            case "manhattan" -> DrinkEnumType.MANHATTAN;
            case "mojito" -> DrinkEnumType.MOJITO;
            case "pina colada" -> DrinkEnumType.PINA_COLADA;
            case "blue lagoon" -> DrinkEnumType.BLUE_LAGOON;
            case "cola" -> DrinkEnumType.COLA;
            case "tea" -> DrinkEnumType.TEA;
            case "coffee" -> DrinkEnumType.COFFEE;
            case "water" -> DrinkEnumType.WATER;
            case "pelican" -> DrinkEnumType.PELICAN;
            case "grenadine" -> DrinkEnumType.GRENADINE;
            case "orange juice" -> DrinkEnumType.ORANGE_JUICE;
            case "milkshake" -> DrinkEnumType.MILKSHAKE;
            default -> null;
        };
    }

    public void chooseType() {
        centerPanel.setBackground(Color.decode("#eeeeee"));
        frame.setTitle("Main page");
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.add(Box.createHorizontalGlue());
        InterfaceWidget dishes = new InterfaceWidget(PATH_CONST + "dishes.png",
                "         DISHES", 256, centerPanel);
        centerPanel.add(Box.createHorizontalGlue());
        InterfaceWidget alcoholDrinks = new InterfaceWidget(PATH_CONST + "alcoDrinks.png",
                "ALCOHOL DRINKS", 256, centerPanel);
        centerPanel.add(Box.createHorizontalGlue());
        InterfaceWidget softDrinks = new InterfaceWidget(PATH_CONST + "nonAlcoDrinks.png",
                "     SOFT DRINKS", 256, centerPanel);
        centerPanel.add(Box.createHorizontalGlue());
        dishes.widget.addMouseListener(chooseItems);
        dishes.widget.addMouseListener(chooseDishes);
        alcoholDrinks.widget.addMouseListener(chooseItems);
        alcoholDrinks.widget.addMouseListener(chooseAlcohol);
        softDrinks.widget.addMouseListener(chooseItems);
        softDrinks.widget.addMouseListener(chooseSoft);
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.setBackground(frameColor);
        ImageIcon exit = new ImageIcon(PATH_CONST + "close.png");
        Image exitImg = exit.getImage();
        exitImg = exitImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        JLabel restaurant1 = new JLabel("DRUNK ");
        JLabel restaurant2 = new JLabel(" SHARK");
        restaurant1.setFont(topFont);
        restaurant2.setFont(topFont);
        northPanel.add(Box.createHorizontalStrut(650));
        northPanel.add(restaurant1);
        JLabel sharkLabel = new JLabel(new ImageIcon(PATH_CONST + "shark.png"));
        northPanel.add(sharkLabel);
        northPanel.add(restaurant2);
        northPanel.add(Box.createHorizontalStrut(582));
        exitLabel = new JLabel(new ImageIcon(exitImg));
        exitLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exitLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        northPanel.add(exitLabel);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
    }

    public void addFood(String[] args) {
        JPanel foodContainer = new JPanel();
        foodContainer.setLayout(new GridLayout(1, 4));
        for (String arg : args) {
            for (int i = 0; i < 36; i++) {
                if (allFoodWidgets[i].textLabel.getText().equals(arg)) {
                    foodContainer.add(allFoodWidgets[i].widget);
                    break;
                }
            }
        }
        centerPanel.add(foodContainer);
    }

    public void chooseDishes() {
        frame.setTitle("Dishes");
        centerPanel.add(Box.createVerticalGlue());
        addFood(new String[]{"bibimbap", "tteok", "kimchi", "ramen"});
        addFood(new String[]{"tom yam", "salad", "green curry", "massaman"});
        addFood(new String[]{"halo halo", "masala dosa", "pasta", "rolls"});
        addFood(new String[]{"sushi", "shaved ice", "takoyaki", "noodle"});
        centerPanel.add(Box.createVerticalGlue());
    }

    public void chooseAlco() {
        frame.setTitle("Alcohol drinks");
        centerPanel.add(Box.createVerticalGlue());
        addFood(new String[]{"vodka", "black russian", "wine", "champagne"});
        addFood(new String[]{"bloody mary", "beer", "rum", "whiskey"});
        addFood(new String[]{"manhattan", "mojito", "pina colada", "blue lagoon"});
        centerPanel.add(Box.createVerticalGlue());
    }

    public void chooseSoft() {
        frame.setTitle("Soft drinks");
        centerPanel.add(Box.createVerticalGlue());
        addFood(new String[]{"cola", "tea", "coffee", "water"});
        addFood(new String[]{"pelican", "grenadine", "orange juice", "milkshake"});
        centerPanel.add(Box.createVerticalGlue());
    }

    public void chooseItems() {
        centerPanel.setBackground(Color.decode("#FFF6ED"));
        northPanel.removeAll();
        returnLabel.removeMouseListener(chooseItems);
        returnLabel.removeMouseListener(chooseDishes);
        returnLabel.removeMouseListener(chooseSoft);
        returnLabel.removeMouseListener(chooseAlcohol);
        returnLabel.addMouseListener(backToMain);

        JLabel northText1 = new JLabel("        CHOOSE ");
        JLabel northText2 = new JLabel(" FROM LIST ");
        northText1.setFont(topFont);
        northText2.setFont(topFont);

        frame.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.add(Box.createHorizontalGlue());
        northPanel.add(returnLabel);
        northPanel.add(Box.createHorizontalStrut(530));
        northPanel.add(northText1);
        ImageIcon menuIcon = new ImageIcon(PATH_CONST + "menu.png");
        Image menuImage = menuIcon.getImage();
        menuImage = menuImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        JLabel menu = new JLabel(new ImageIcon(menuImage));
        northPanel.add(menu);
        northPanel.add(northText2);
        for (int i = 0; i < 24; i++)
            northPanel.add(Box.createHorizontalGlue());
        northPanel.add(exitLabel);
        northPanel.setBackground(frameColor);
        frame.repaint();

        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        scrollPane.getVerticalScrollBar().setBackground(frameColor);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.decode("#9cbac4");
            }
        });
        scrollPane.setViewportView(centerPanel);
        frame.add(scrollPane);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAutoscrolls(true);

        frame.repaint();
        frame.revalidate();
    }

    public void showInfo() {
        centerPanel.setLayout(new GridLayout(1, 2));
        InterfaceWidget curWidget = allFoodWidgets[InterfaceWidget.curPos];
        returnLabel.removeMouseListener(backToMain);
        returnLabel.addMouseListener(chooseItems);
        returnLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (curWidget.type == null)
            returnLabel.addMouseListener(chooseDishes);
        else {
            if (curWidget.type.isAlcoholic())
                returnLabel.addMouseListener(chooseAlcohol);
            else
                returnLabel.addMouseListener(chooseSoft);
        }
        Image img = curWidget.imgIcon.getImage();
        img = img.getScaledInstance(512, 512, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        centerPanel.add(imgLabel);
        Font font = new Font("a", Font.ITALIC, 30);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1));
        infoPanel.setBackground(Color.decode("#FFF6ED"));
        JLabel text = new JLabel(curWidget.textLabel.getText());
        text.setFont(font);
        infoPanel.add(text);
        JLabel description;
        if (!curWidget.description.equals(""))
            description = new JLabel(curWidget.description);
        else description = new JLabel(curWidget.degreesLabel.getText());
        description.setFont(font);
        infoPanel.add(description);
        JLabel priceLabel = new JLabel(curWidget.priceLabel.getText());
        priceLabel.setFont(font);
        infoPanel.add(priceLabel);
        JPanel choosePanel = new JPanel();
        choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.X_AXIS));
        JButton _minusButton = new JButton("-");
        JButton _plusButton = new JButton("+");
        choosePanel.setBackground(Color.decode("#FFF6ED"));
        choosePanel.add(Box.createHorizontalGlue());
        choosePanel.add(_minusButton);
        curWidget.countOfChosenForInfo.setHorizontalAlignment(SwingConstants.CENTER);
        curWidget.countOfChosenForInfo.setFont(new Font("count", Font.BOLD, 40));
        choosePanel.add(Box.createHorizontalGlue());
        choosePanel.add(curWidget.countOfChosenForInfo);
        choosePanel.add(Box.createHorizontalGlue());
        choosePanel.add(_plusButton);
        choosePanel.add(Box.createHorizontalGlue());
        _minusButton.setBackground(Color.decode("#A6794D"));
        _plusButton.setBackground(Color.decode("#A6794D"));
        _minusButton.setFont(new Font("count", Font.BOLD, 40));
        _plusButton.setFont(new Font("count", Font.BOLD, 40));
        _minusButton.addMouseListener(foodRemoved);
        _plusButton.addMouseListener(foodAdded);
        infoPanel.add(choosePanel);
        centerPanel.add(infoPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void myOrder() {
        frame.remove(southPanel);
        northPanel.removeAll();
        centerPanel.removeAll();
        centerPanel.setBackground(Color.decode("#eeeeee"));
        frame.remove(scrollPane);

        JLabel northText1 = new JLabel("           YOUR  ");
        JLabel northText2 = new JLabel("  ORDER");
        northText1.setFont(topFont);
        northText2.setFont(topFont);

        frame.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.add(Box.createHorizontalGlue());
        northPanel.add(Box.createHorizontalStrut(585));
        northPanel.add(northText1);
        ImageIcon orderIcon = new ImageIcon(PATH_CONST + "order.png");
        Image menuImage = orderIcon.getImage();
        menuImage = menuImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        JLabel menu = new JLabel(new ImageIcon(menuImage));
        northPanel.add(menu);
        northPanel.add(northText2);
        northPanel.add(Box.createHorizontalStrut(581));
        northPanel.add(exitLabel);
        northPanel.add(Box.createHorizontalStrut(350));
        northPanel.setBackground(frameColor);
        int count = chosenWidgets.size();
        centerPanel.setLayout(new GridLayout(count / 2 + 2, 2));

        for (int key : chosenWidgets.keySet()) {
            JPanel orderWidget = new JPanel();
            orderWidget.setLayout(new GridLayout(1, 6));
            InterfaceWidget curWidget = chosenWidgets.get(key);
            Image curIcon = curWidget.imgIcon.getImage();
            curIcon = curIcon.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(curIcon));
            orderWidget.add(imgLabel);
            if (!curWidget.countOfChosen.getText().equals("1")) {
                JLabel countText = new JLabel("x" + curWidget.countOfChosen.getText());
                countText.setFont(new Font("count", Font.BOLD, 30));
                countText.setForeground(Color.decode("#E39E5D"));
                orderWidget.add(countText);
            } else
                orderWidget.add(new JLabel(""));
            JLabel nameText = new JLabel(curWidget.textLabel.getText());
            nameText.setFont(new Font("name", Font.PLAIN, 30));
            orderWidget.add(nameText);
            JLabel priceText = new JLabel("  " + Integer.parseInt(curWidget.countOfChosen.getText())
                    * curWidget.price + "$");
            priceText.setFont(new Font("price", Font.ITALIC, 30));
            priceText.setForeground(Color.decode("#3B808C"));
            orderWidget.add(priceText);
            centerPanel.add(orderWidget);
        }
        if (count != 0) {
            JLabel totalPrice = new JLabel("Total price: " + iOrder.costTotal() + "$      ");
            totalPrice.setForeground(Color.decode("#333999"));
            totalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
            totalPrice.setFont(new Font("b", Font.ITALIC, 24));
            centerPanel.add(Box.createHorizontalGlue());
            if (count % 2 != 0) {
                centerPanel.add(Box.createHorizontalGlue());
            }
            centerPanel.add(totalPrice);
        }
        scrollPane.setViewportView(centerPanel);
        frame.add(scrollPane);

        payPanel.setLayout(new GridLayout(1, 3));
        JButton returnButton = new JButton("Order more");
        returnButton.setForeground(Color.decode("#339999"));
        returnButton.setBackground(Color.decode("#D7E8EC"));
        returnButton.setFont(new Font("", Font.BOLD, 32));
        returnButton.addMouseListener(backToMain);
        payPanel.add(returnButton);
        if (categoryLabel.getText().equals("Category: Adult")) {
            JLabel warning1 = new JLabel("                         The order contains alcohol");
            JLabel warning2 = new JLabel("               Present your passport at the checkout");
            warning1.setForeground(Color.decode("#A64E45"));
            warning1.setFont(new Font("", Font.BOLD, 20));
            warning2.setForeground(Color.decode("#A64E45"));
            warning2.setFont(new Font("", Font.BOLD, 20));
            JPanel warningPanel = new JPanel();
            warningPanel.setLayout(new BoxLayout(warningPanel, BoxLayout.Y_AXIS));
            warningPanel.add(warning1);
            warningPanel.add(warning2);
            warningPanel.setBackground(frameColor);
            payPanel.add(warningPanel);
        }
        JButton payButton = new JButton("Complete order");
        payButton.setForeground(Color.decode("#339999"));
        payButton.setBackground(Color.decode("#D7E8EC"));
        payButton.setFont(new Font("", Font.BOLD, 32));
        payButton.addMouseListener(endOrder);
        payPanel.setBackground(frameColor);
        payPanel.add(payButton);

        frame.add(payPanel, BorderLayout.SOUTH);
        frame.repaint();
        frame.revalidate();
    }

    public void end() {
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel endText1 = new JLabel("        Your order is being prepared!");
        JLabel endText2 = new JLabel("                      Want to get new order?");
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(endText1);
        endText1.setHorizontalAlignment(SwingConstants.CENTER);
        endText1.setFont(new Font("", Font.BOLD, 40));
        endText1.setForeground(Color.decode("#339999"));
        centerPanel.add(endText2);
        endText2.setHorizontalAlignment(SwingConstants.CENTER);
        endText2.setFont(new Font("", Font.BOLD, 30));
        endText2.setForeground(Color.decode("#339999"));
        JButton yesButton = new JButton("NEW ORDER");
        yesButton.setFont(new Font("", Font.BOLD, 30));
        yesButton.setBackground(Color.decode("#b5d3d8"));
        yesButton.setForeground(Color.decode("#339999"));
        yesButton.addMouseListener(backToMain);
        JButton noButton = new JButton(" FINISH ");
        noButton.setFont(new Font("", Font.BOLD, 30));
        noButton.setBackground(Color.decode("#b5d3d8"));
        noButton.setForeground(Color.decode("#339999"));
        noButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(yesButton);
        buttonPanel.add(Box.createHorizontalStrut(100));
        buttonPanel.add(noButton);
        buttonPanel.add(Box.createHorizontalGlue());
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue());
        frame.repaint();
        frame.revalidate();
    }
}
