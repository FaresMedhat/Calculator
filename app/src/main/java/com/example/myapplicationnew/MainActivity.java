package com.example.myapplicationnew;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapplicationnew.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding binding;
   StringBuilder input  = new StringBuilder() ;
   ArrayList<Double>number ;
   ArrayList<Character> operators;
   double result ;
   double FinalResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        View[] numbers = {
                binding.zero, binding.one, binding.two, binding.three, binding.four,
                binding.five, binding.six, binding.seven, binding.eight, binding.nine
        };
        for(View button:numbers){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    input.append(((Button)button).getText().toString());
                    binding.displayOperation.setText(input.toString());
                }
            });

        }
        View[] operations = {
                binding.add, binding.module, binding.multiply, binding.substract,
                binding.decimal};
        for(View button2:operations){
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    input.append(((Button)button2).getText().toString());
                    binding.displayOperation.setText(input.toString());
                }
            });

        }


    binding.equal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String numOfOperation=   binding.displayOperation.getText().toString();
           FinalResult= calculation(numOfOperation);

            binding.result.setText(String.valueOf(FinalResult));
            binding.displayOperation.setText("");
        }
    });
        binding.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setLength(0);
                binding.result.setText("");
                binding.displayOperation.setText("");
            }
        });

    }

    public double calculation(String numOfOperation) {
        number = new ArrayList<>();
        operators = new ArrayList<>();

        // Check if input is empty
        if (numOfOperation.isEmpty()) {
            return 0.0;
        }


        String[] tokens = numOfOperation.split("(?<=[+\\-*/])|(?=[+\\-*/])");

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (token.matches("[+\\-*/]")) {
                operators.add(token.charAt(0));
            } else {
                try {
                    number.add(Double.parseDouble(token));
                } catch (NumberFormatException e) {
                    return 0.0; // Return 0 if parsing fails
                }
            }
        }


        if (number.size() == 1 && operators.isEmpty()) {
            return number.get(0);
        }


        if (number.size() != operators.size() + 1) {
            return 0.0;
        }


        int i = 0;
        while (i < operators.size()) {
            if (operators.get(i) == '*') {
                number.set(i, number.get(i) * number.get(i + 1));
                number.remove(i + 1);
                operators.remove(i);
            } else if (operators.get(i) == '/') {
                if (number.get(i + 1) == 0) {
                    return 0.0; // Prevent division by zero
                }
                number.set(i, number.get(i) / number.get(i + 1));
                number.remove(i + 1);
                operators.remove(i);
            } else {
                i++;
            }
        }


        i = 0;
        while (i < operators.size()) {
            if (operators.get(i) == '+') {
                number.set(i, number.get(i) + number.get(i + 1));
                number.remove(i + 1);
                operators.remove(i);
            } else if (operators.get(i) == '-') {
                number.set(i, number.get(i) - number.get(i + 1));
                number.remove(i + 1);
                operators.remove(i);
            } else {
                i++; 
            }
        }

        return number.get(0);
    }

}