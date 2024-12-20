import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DinoGame extends JPanel implements ActionListener {
    private Timer timer; // Bộ đếm thời gian để cập nhật trò chơi
    private int dinoY = 200; // Vị trí dọc của khủng long
    private int obstacleX = 400; // Vị trí ngang của chướng ngại vật
    private boolean jumping = false; // Trạng thái nhảy của khủng long
    private int jumpSpeed = 0; // Tốc độ nhảy của khủng long

    public DinoGame() {
        timer = new Timer(20, this); // Cập nhật trò chơi mỗi 20ms
        timer.start(); // Bắt đầu bộ đếm thời gian
        setFocusable(true); // Đảm bảo panel có thể nhận các sự kiện bàn phím
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE && !jumping) {
                    jumping = true; // Bắt đầu nhảy khi nhấn phím cách
                    jumpSpeed = -10; // Thiết lập tốc độ nhảy ban đầu
                } else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_R) {
                    restartGame(); // Khởi động lại trò chơi khi nhấn phím R
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE); // Tô nền trắng
        g.fillRect(0, 0, getWidth(), getHeight()); // Vẽ nền

        g.setColor(Color.BLACK); // Màu đen cho khủng long và chướng ngại vật
        g.fillRect(50, dinoY, 30, 30); // Vẽ khủng long (hình vuông 30x30)
        g.fillRect(obstacleX, 220, 20, 30); // Vẽ chướng ngại vật (hình chữ nhật 20x30)

        g.drawLine(0, 250, getWidth(), 250); // Vẽ đường mặt đất
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jumping) {
            dinoY += jumpSpeed; // Cập nhật vị trí khủng long khi nhảy
            jumpSpeed += 1; // Tăng tốc độ rơi xuống (giả lập trọng lực)

            if (dinoY >= 200) { // Khi khủng long chạm đất
                dinoY = 200; // Đặt lại vị trí dọc
                jumping = false; // Kết thúc trạng thái nhảy
            }
        }

        obstacleX -= 5; // Di chuyển chướng ngại vật sang trái
        if (obstacleX < -20) { // Khi chướng ngại vật đi ra khỏi màn hình
            obstacleX = getWidth(); // Đặt lại vị trí chướng ngại vật
        }

        if (new Rectangle(50, dinoY, 30, 30).intersects(new Rectangle(obstacleX, 220, 20, 30))) {
            timer.stop(); // Dừng trò chơi nếu va chạm xảy ra
            JOptionPane.showMessageDialog(this, "Game Over! Press 'R' to Restart."); // Hiển thị thông báo "Game Over" với hướng dẫn restart
        }

        repaint(); // Vẽ lại giao diện
    }

    private void restartGame() {
        dinoY = 200; // Đặt lại vị trí khủng long
        obstacleX = 400; // Đặt lại vị trí chướng ngại vật
        jumping = false; // Đặt lại trạng thái nhảy
        jumpSpeed = 0; // Đặt lại tốc độ nhảy
        timer.start(); // Bắt đầu lại bộ đếm thời gian
        repaint(); // Vẽ lại giao diện
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dino Game"); // Tạo cửa sổ
        DinoGame game = new DinoGame(); // Tạo trò chơi
        frame.add(game); // Thêm trò chơi vào cửa sổ
        frame.setSize(800, 300); // Đặt kích thước cửa sổ
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Thoát khi đóng cửa sổ
        frame.setVisible(true); // Hiển thị cửa sổ
    }
}
