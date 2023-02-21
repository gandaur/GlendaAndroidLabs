package algonquin.cst2335.anda0017;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * first page of app
 * @author Glenda Andaur
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {

    /** Message at the centre of the screen*/
    TextView passwordMessage = null;
    /** Receives password input from user*/
    EditText passwordField = null;
    /** Button that checks the input password when clicked*/
    Button loginButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordMessage = findViewById(R.id.password_message);
        passwordField = findViewById(R.id.password_field);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener( clk -> {
            String password = passwordField.getText().toString();

            if (checkPasswordComplexity( password )) {
                passwordMessage.setText("Your password is complex enough");
            } else {
                passwordMessage.setText("You shall not pass!");
            }
        });
    }

    /**
     * Validates given password for at least one uppercase, one lowercase, one number, and one special character.
     * Gives out a message if it doesn't meet a certain requirement
     * @param password password that is grabbed from the EditText
     * @return true if password meets the requirements; false if not
     */
    boolean checkPasswordComplexity(String password) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = false;
        foundLowerCase = false;
        foundNumber = false;
        foundSpecial = false;
        char[] chars = password.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                foundNumber = true;
            }
            if (Character.isUpperCase(chars[i])) {
                foundUpperCase = true;
            }

            if (Character.isLowerCase(chars[i])) {
                foundLowerCase = true;
            }
            if (isSpecialCharacter(chars[i])) {
                foundSpecial = true;
            }
        }

        if (!foundUpperCase) {
            Toast.makeText(this, "Password must contain at lest one uppercase!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!foundLowerCase) {
            Toast.makeText(this, "Password must contain at lest one lowercase!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!foundNumber) {
            Toast.makeText(this, "Password must contain at lest one number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!foundSpecial) {
            Toast.makeText(this, "Password must contain at lest one special character!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Checks if a given character is a special character
     * @param c a character
     * @return true if it's a special character; false if not
     */
    boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '?':
            case '*':
            case '!':
            case '%':
            case '^':
            case '@':
            case '$':
            case '&':
                return true;
            default:
                return false;
        }
    }
}