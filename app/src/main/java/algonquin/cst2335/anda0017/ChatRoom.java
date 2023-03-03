package algonquin.cst2335.anda0017;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.anda0017.data.ChatMessage;
import algonquin.cst2335.anda0017.data.ChatMessageDAO;
import algonquin.cst2335.anda0017.data.ChatRoomViewModel;
import algonquin.cst2335.anda0017.data.MessageDatabase;
import algonquin.cst2335.anda0017.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.anda0017.databinding.ReceiveMessageBinding;
import algonquin.cst2335.anda0017.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
        private RecyclerView.Adapter<MyRowHolder> myAdapter;
        ArrayList<ChatMessage> messages;
        ChatRoomViewModel chatModel;
        ChatMessageDAO mDAO;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
            messages = chatModel.messages.getValue();

            MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MessageDatabase").build();
            mDAO = db.cmDAO();

            if (messages == null) {
                chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());

                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> {
                    messages.addAll(mDAO.getAllMessage());
                    runOnUiThread( () -> binding.recycleView.setAdapter(myAdapter));
                });
            }

            myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
                @NonNull
                @Override
                public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    if (viewType == 0) {
                        SentMessageBinding sendBinding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                        return new MyRowHolder(sendBinding.getRoot());
                    } else {
                        ReceiveMessageBinding receiveBinding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                        return new MyRowHolder(receiveBinding.getRoot());
                    }
                }

                @Override
                public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                    holder.messageText.setText("");
                    holder.timeText.setText("");

                    ChatMessage obj = messages.get(position);
                    holder.messageText.setText(obj.getMessage());
                    holder.timeText.setText(obj.getTimeSent());
                }

                @Override
                public int getItemCount() {
                    return messages.size();
                }

                @Override
                public int getItemViewType(int position) {
                    if (messages.get(position).isSentButton() == true) {
                        return 0;
                    } else {
                        return 1;
                    }

                }
            };

//        chatModel.selectedMessage.observe(this, (newMessageValue) -> {
//            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragmentLocation, chatFragment)
//                    .commit();
//        });

            binding.sendButton.setOnClickListener(click -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyy hh-mm-ss a");
                String currentDateandTime = sdf.format(new Date());
                ChatMessage newMessage = new ChatMessage();
                newMessage.setMessage(binding.textInput.getText().toString());
                newMessage.setTimeSent(currentDateandTime);
                newMessage.setSentButton(true);
                messages.add(newMessage);

                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> {
                    newMessage.id = (int) mDAO.insertMessage(newMessage);
                });
                myAdapter.notifyItemInserted(messages.size() - 1);
                binding.textInput.setText("");
            });

            binding.receiveButton.setOnClickListener(click -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyy hh-mm-ss a");
                String currentDateandTime = sdf.format(new Date());
                ChatMessage newMessage2 = new ChatMessage();
                newMessage2.setMessage(binding.textInput.getText().toString());
                newMessage2.setTimeSent(currentDateandTime);
                newMessage2.setSentButton(false);
                messages.add(newMessage2);
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> {
                    newMessage2.setId((int) mDAO.insertMessage(newMessage2));
                });
                myAdapter.notifyItemInserted(messages.size() - 1);
                binding.textInput.setText("");
            });
            binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        }
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Do you want to delete the message: " + messageText.getText())
                        .setTitle("Question:")
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            ChatMessage m = messages.get(position);
                            Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", clik ->{
                                        Executor thread = Executors.newSingleThreadExecutor();
                                        thread.execute(() -> {
                                            mDAO.insertMessage(m);
                                        });
                                        chatModel.messages.getValue().add(m);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() -> {
                                mDAO.deleteMessage(m);
                            });
                            myAdapter.notifyItemRemoved(position);
                            chatModel.messages.getValue().remove(position);
                        })
                        .setNegativeButton("No", (dialog, cl) -> { })
                        .create()
                        .show();
            });
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);

        }
    }

}
