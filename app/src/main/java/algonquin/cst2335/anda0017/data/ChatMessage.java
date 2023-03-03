
package algonquin.cst2335.anda0017.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
    public class ChatMessage {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        public int id;

        @ColumnInfo(name = "message")
        protected String message;

        @ColumnInfo(name = "TimeSent")
        protected String timeSent;

        @ColumnInfo(name = "SendOrReceive")
        protected boolean isSentButton;

        public ChatMessage(String m, String t, boolean sent) {
            message = m;
            timeSent = t;
            isSentButton = sent;
        }

    public ChatMessage() {

    }

    public String getMessage() {
            return message;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setTimeSent(String timeSent) {
            this.timeSent = timeSent;
        }

        public void setSentButton(boolean sentButton) {
            isSentButton = sentButton;
        }

        public int getId() {
            return id;
        }

        public boolean isSentButton() {
            return isSentButton;
        }
    }