package view;

public class Activity {
    public static void main(String[] args){

        Button btn = new Button();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn.performClick();

    }

}
