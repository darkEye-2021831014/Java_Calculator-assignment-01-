import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Calculator {
    private JFrame frame;
    private JLabel screen;
    private JPanel buttonPanel;
    private int width, height, screenWidth, screenHeight, fontSize;
    final int row = 5, column = 5;
    private Map<String, Integer> buttonValues;
    private double prev = 0, cur = 0, ans = 0;
    private StringBuilder onScreen = new StringBuilder();
    DecimalFormat point = new DecimalFormat("#.####");
    private int curOperation = -1; // 0=+,1=-,2=*,3=/

    Calculator(int width, int height) {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setValues(width, height);
        createScreen();
        buttonPanel = new JPanel();
        buttonPanel.setBounds(0, this.screenHeight, this.screenWidth, this.height - this.screenHeight - 28);
        buttonPanel.setLayout(new GridLayout(this.row, this.column));
        createNormalMode();

        frame.add(screen, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setSize(this.width, this.height);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void setValues(int width, int height) {
        this.width = width;
        this.height = height;
        this.screenWidth = width;
        this.screenHeight = (2 * height) / 10;
        this.fontSize = 50;
    }

    private void createScreen() {
        this.screen = new JLabel("0 ", JLabel.RIGHT);
        this.screen.setToolTipText("You Can Not Edit Here");
        this.screen.setFont(new Font("./Fonts/robotoMonoBold.ttf", Font.BOLD | Font.ITALIC, this.fontSize));
        this.screen.setOpaque(true);
        this.screen.setBackground(Color.BLACK);
        this.screen.setVerticalAlignment(JLabel.BOTTOM);
        this.screen.setForeground(Color.CYAN);
        this.screen.setBounds(0, 0, this.screenWidth, this.screenHeight);
    }

    private void createNormalMode() {
        buttonValues = new HashMap<>();
        JButton[] buttons = new JButton[this.row * this.column];

        int index = 0;
        buttons[index++] = new JButton("%");
        buttons[index++] = new JButton("X\u00B2"); // power 2
        buttons[index++] = new JButton("X\u00B3"); // power 3
        buttons[index++] = new JButton("DEL");
        buttons[index++] = new JButton("AC");

        buttons[index++] = new JButton("7");
        buttons[index++] = new JButton("8");
        buttons[index++] = new JButton("9");
        buttons[index++] = new JButton("\u221A"); // square root
        buttons[index++] = new JButton("\u221B"); // cube root

        buttons[index++] = new JButton("4");
        buttons[index++] = new JButton("5");
        buttons[index++] = new JButton("6");
        buttons[index++] = new JButton("\u00F7");
        buttons[index++] = new JButton("\u00D7");

        buttons[index++] = new JButton("1");
        buttons[index++] = new JButton("2");
        buttons[index++] = new JButton("3");
        buttons[index++] = new JButton("+");
        buttons[index++] = new JButton("-");

        buttons[index++] = new JButton("0");
        buttons[index++] = new JButton(".");
        buttons[index++] = new JButton("Ans");
        buttons[index++] = new JButton("+/-");
        buttons[index++] = new JButton("=");

        index = 0;
        for (JButton button : buttons) {
            buttonValues.put(button.getText(), index++);
            button.setOpaque(true);
            button.setBackground(Color.CYAN);
            this.screen.setFont(new Font("./Fonts/robotoMonoBold.ttf", Font.BOLD | Font.ITALIC, this.fontSize));
            // event handling
            HandleButtonEvent(button);
            buttonPanel.add(button);
        }
    }

    private void HandleButtonEvent(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int value = buttonValues.get(button.getText());

                switch (value) {
                    case 0:
                        percentageOperation();
                        break;
                    case 1:
                        squareOperation();
                        break;
                    case 2:
                        cubeOperation();
                        break;
                    case 3:
                        deleteOperation();
                        break;
                    case 4:
                        allClearOperation();
                        break;
                    case 5:
                        setValueOnScreen(7);
                        break;
                    case 6:
                        setValueOnScreen(8);
                        break;
                    case 7:
                        setValueOnScreen(9);
                        break;
                    case 8:
                        squareRootOperation();
                        break;
                    case 9:
                        cubeRootOperation();
                        break;
                    case 10:
                        setValueOnScreen(4);
                        break;
                    case 11:
                        setValueOnScreen(5);
                        break;
                    case 12:
                        setValueOnScreen(6);
                        break;
                    case 13:
                        divisionOperation();
                        break;
                    case 14:
                        multiplyOperation();
                        break;
                    case 15:
                        setValueOnScreen(1);
                        break;
                    case 16:
                        setValueOnScreen(2);
                        break;
                    case 17:
                        setValueOnScreen(3);
                        break;
                    case 18:
                        additionOperation();
                        break;
                    case 19:
                        subtractionOperation();
                        break;
                    case 20:
                        setValueOnScreen(0);
                        break;
                    case 21:
                        setValueOnScreen(-1);// -1 means dot(.)
                        break;
                    case 22:
                        setAnswerOnScreen();
                        break;
                    case 23:
                        setNegativeValue();
                        break;
                    case 24:
                        equalOperation();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void renderOnScreen() {
        screen.setText(onScreen.toString() + " ");
    }

    private void getCurValue() {
        if (!onScreen.isEmpty())
            cur = Double.parseDouble(onScreen.toString());
    }

    private void percentageOperation() {
        getCurValue();
        if (cur != 0) {
            cur /= 100.0;
            onScreen.setLength(0);
            onScreen.append(point.format(cur));
            renderOnScreen();
        }
    }

    private void squareOperation() {
        getCurValue();
        if (cur != 0) {
            cur *= cur;
            onScreen.setLength(0);
            onScreen.append(point.format(cur));
            renderOnScreen();
        }
    }

    private void cubeOperation() {
        getCurValue();
        if (cur != 0) {
            cur *= (cur * cur);
            onScreen.setLength(0);
            onScreen.append(point.format(cur));
            renderOnScreen();
        }
    }

    private void deleteOperation() {
        if (!onScreen.isEmpty()) {
            onScreen.deleteCharAt(onScreen.length() - 1);
            renderOnScreen();
        }
        if (onScreen.isEmpty())
            screen.setText("0 ");

    }

    private void allClearOperation() {
        this.curOperation = -1;
        this.cur = 0;
        this.prev = 0;
        this.ans = 0;

        onScreen.setLength(0); // empty string;
        screen.setText("0 ");
    }

    private void setValueOnScreen(int value) {
        if (value == -1 && !onScreen.isEmpty())
            onScreen.append('.');
        else if (value != -1)
            onScreen.append(value);
        renderOnScreen();
    }

    private void squareRootOperation() {
        getCurValue();
        if (cur != 0) {
            cur = Math.sqrt(cur);
            onScreen.setLength(0);
            onScreen.append(point.format(cur));
            renderOnScreen();
        }
    }

    private void cubeRootOperation() {
        getCurValue();
        if (cur != 0) {
            cur = Math.cbrt(cur);
            onScreen.setLength(0);
            onScreen.append(point.format(cur));
            renderOnScreen();
        }
    }

    private void divisionOperation() {
        getCurValue();
        curOperation = 3;
        if (cur != 0) {
            prev = Double.parseDouble(onScreen.toString());
            cur = 0;
            onScreen.setLength(0);
            screen.setText("0 ");
        }
    }

    private void multiplyOperation() {
        getCurValue();
        curOperation = 2;
        if (cur != 0) {
            prev = Double.parseDouble(onScreen.toString());
            cur = 0;
            onScreen.setLength(0);
            screen.setText("0 ");
        }
    }

    private void additionOperation() {
        getCurValue();
        curOperation = 0;
        if (cur != 0) {
            prev = Double.parseDouble(onScreen.toString());
            cur = 0;
            onScreen.setLength(0);
            screen.setText("0 ");
        }
    }

    private void subtractionOperation() {
        getCurValue();
        curOperation = 1;
        if (cur != 0) {
            prev = Double.parseDouble(onScreen.toString());
            cur = 0;
            onScreen.setLength(0);
            screen.setText("0 ");
        }
    }

    private void setAnswerOnScreen() {
        if (ans != 0) {
            onScreen.setLength(0);
            onScreen.append(point.format(ans));
            renderOnScreen();
        }
    }

    private void setNegativeValue() {
        getCurValue();
        if (cur != 0) {
            cur *= -1;
            onScreen.setLength(0);
            onScreen.append(point.format(cur));
            renderOnScreen();
        }
    }

    private void equalOperation() {
        getCurValue();
        if (cur != 0 || curOperation != 3) {
            switch (curOperation) {
                case 0:
                    ans = cur + prev;
                    break;
                case 1:
                    ans = prev - cur;
                    break;
                case 2:
                    ans = cur * prev;
                    break;
                case 3:
                    ans = prev / cur;
                    break;
                default:
                    break;
            }
            onScreen.setLength(0);
            onScreen.append(point.format(ans));
            renderOnScreen();
        }

    }
}
