package com.example.hybridapp_example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.sdk.wisetracker.new_dot.open.DOT;

import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends Fragment {


    @Override
    public void onResume() {
        super.onResume();
        // Natvie Activity 화면 전환 감지
        DOT.onStartPage(getActivity());

        Map<String,Object> screenData = new HashMap<>();
        screenData.put("event", "first_fragment");
        DOT.logScreen(screenData);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
}