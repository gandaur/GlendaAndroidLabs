

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.anda0017.data.ChatMessage;
import algonquin.cst2335.anda0017.databinding.DetailLayoutBinding;

public class MessageDetailsFragment extends Fragment {
    ChatMessage selected;

    public MessageDetailsFragment(ChatMessage m) {
        selected = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        DetailLayoutBinding binding = DetailLayoutBinding.inflate(inflater);
        binding.messageView.setText(selected.getMessage());
        binding.timeView.setText(selected.getTimeSent());
        binding.databaseIdView.setText("Id = " + selected.id);
        return binding.getRoot();
    }
}