import javax.swing.*;
import java.nio.file.Paths;

public class Assets {
    public static ImageIcon smiley, sad, omouth, glasses, bomb, bombRed, blank, flag, one, two, three, four, five, six, seven, eight, revealed, wrongFlag, questionMark, infoLeft, infoRight, player, playerButton;

    public Assets() {
        smiley = new ImageIcon(getClass().getResource("img/smiley.png"));
        sad = new ImageIcon(getClass().getResource("img/sad.png"));
        omouth = new ImageIcon(getClass().getResource("img/omouth.png"));
        glasses = new ImageIcon(getClass().getResource("img/glasses.png"));
        bomb = new ImageIcon(getClass().getResource("img/bomb.png"));
        bombRed = new ImageIcon(getClass().getResource("img/bombred.png"));
        blank = new ImageIcon(getClass().getResource("img/blank.png"));
        flag = new ImageIcon(getClass().getResource("img/flag.png"));
        one = new ImageIcon(getClass().getResource("img/one.png"));
        two = new ImageIcon(getClass().getResource("img/two.png"));
        three = new ImageIcon(getClass().getResource("img/three.png"));
        four = new ImageIcon(getClass().getResource("img/four.png"));
        five = new ImageIcon(getClass().getResource("img/five.png"));
        six = new ImageIcon(getClass().getResource("img/six.png"));
        seven = new ImageIcon(getClass().getResource("img/seven.png"));
        eight = new ImageIcon(getClass().getResource("img/eight.png"));
        revealed = new ImageIcon(getClass().getResource("img/revealed.png"));
        wrongFlag = new ImageIcon(getClass().getResource("img/wrongflag.png"));
        questionMark = new ImageIcon(getClass().getResource("img/questionMark.png"));
        infoLeft = new ImageIcon(getClass().getResource("img/infoLeft.png"));
        infoRight = new ImageIcon(getClass().getResource("img/infoRight.png"));
        player = new ImageIcon(getClass().getResource("img/player.png"));
        playerButton = new ImageIcon(getClass().getResource("img/playerButton.png"));
    }
}
