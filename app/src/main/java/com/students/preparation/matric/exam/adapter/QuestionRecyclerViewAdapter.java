package com.students.preparation.matric.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.admin.RegisterQuestionsFragment;
import com.students.preparation.matric.exam.model.Questions;

import java.util.ArrayList;


public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Questions> referenceBooks;
    private String questionText;
    private StringBuilder packedQuestion = new StringBuilder();
    private String selectedAnswer="",selectedQuestionType="";
    private RegisterQuestionsFragment registerQuestionsFragment;
    public QuestionRecyclerViewAdapter(RegisterQuestionsFragment fragment,Context context, ArrayList<Questions> tutorialsArrayList) {
        this.context = context;
        this.referenceBooks = tutorialsArrayList;
        this.registerQuestionsFragment = fragment;
    }

    @NonNull
    @Override
    public QuestionRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.questions_recyclerview_layout, viewGroup, false);
        return new QuestionRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final Questions singleTutorial = referenceBooks.get(i);
        viewHolder.question.setHint(singleTutorial.getQuestions());

        final String[] questionTypeArray = context.getResources().getStringArray(R.array.questionTypes);
        ArrayAdapter<CharSequence> questionAdapter = new ArrayAdapter<CharSequence>(context,
                R.layout.spinner_text, questionTypeArray );
        questionAdapter.setDropDownViewResource(R.layout.simple_spinner_drop_down);
        viewHolder.questionsType.setAdapter(questionAdapter);
        viewHolder.questionsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = ((TextView)view).getText().toString();
                if(!text.equals(questionTypeArray[0])){
                    selectedQuestionType = text;
                    if (selectedQuestionType.equals("Image")){
                        viewHolder.imageAcceptorLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String[] gradesArray = context.getResources().getStringArray(R.array.answer_choices);
        ArrayAdapter<CharSequence> gradeAdapter = new ArrayAdapter<CharSequence>(context,
                R.layout.spinner_text, gradesArray );
        gradeAdapter.setDropDownViewResource(R.layout.simple_spinner_drop_down);
        viewHolder.answerSpinner.setAdapter(gradeAdapter);
        viewHolder.answerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = ((TextView)view).getText().toString();
                if(!text.equals(gradesArray[0])){
                    selectedAnswer = text;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = viewHolder.question.getText().toString();
                String choice1 = viewHolder.choice1.getText().toString();
                String choice2 = viewHolder.choice2.getText().toString();
                String choice3 = viewHolder.choice3.getText().toString();
                String choice4 = viewHolder.choice4.getText().toString();
                String answers = selectedAnswer;
                String questionType = selectedQuestionType;
                String explanations = viewHolder.explanations.getText().toString();
                String base64Image = "";
                if (questionType.equals("Image")){
                    base64Image = viewHolder.base64Image.getText().toString();
                }
                if (question.equals("")){
                    Toast.makeText(context,
                            "Please enter question",
                            Toast.LENGTH_LONG).show();
                }else if (choice1.equals("")){
                    Toast.makeText(context, "Please enter choice 1  ", Toast.LENGTH_LONG).show();
                }else if (choice2.equals("")){
                    Toast.makeText(context, "Please enter choice 2  ", Toast.LENGTH_LONG).show();
                }else if (choice3.equals("")){
                    Toast.makeText(context, "Please enter choice 3  ", Toast.LENGTH_LONG).show();
                }else if (choice4.equals("")){
                    Toast.makeText(context, "Please enter choice 4  ", Toast.LENGTH_LONG).show();
                }else if (answers.equals("")){
                    Toast.makeText(context, "Please Select answer", Toast.LENGTH_LONG).show();
                }else if (explanations.equals("")){
                    Toast.makeText(context, "Please enter answer explanations ", Toast.LENGTH_LONG).show();
                }else if (questionType.equals("")){
                    Toast.makeText(context,"Please select question type",Toast.LENGTH_LONG).show();
                }else if (questionType.equals("Image")&&base64Image.equals("")){
                    Toast.makeText(context,"Please enter base64 image text",Toast.LENGTH_LONG).show();
                } else {
                    Questions JSONquestions = new Questions();
                    JSONquestions.setQuestionNumber(singleTutorial.getQuestionNumber());
                    JSONquestions.setType(questionType);
                    JSONquestions.setBase64Image(base64Image);
                    JSONquestions.setQuestions(question);
                    JSONquestions.setChoice1(choice1);
                    JSONquestions.setChoice2(choice2);
                    JSONquestions.setChoice3(choice3);
                    JSONquestions.setChoice4(choice4);
                    JSONquestions.setAnswer(answers);
                    JSONquestions.setExplanations(explanations);
                    boolean result =registerQuestionsFragment.addQuestion(JSONquestions);
                    if (result){
                        registerQuestionsFragment.nextQuestion();
                    }
                }
               //
            }
        });

    }

    @Override
    public int getItemCount() {
        return referenceBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final EditText question,choice1,choice2,choice3,choice4,explanations;
        private final Button nextQuestionBtn;
        private final Spinner answerSpinner;
        private final Spinner questionsType;
        private final EditText base64Image;
        private final LinearLayout imageAcceptorLayout;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.questionEditText);

            choice1 = itemView.findViewById(R.id.enterChoice1);
            choice2 = itemView.findViewById(R.id.enterChoice2);
            choice3 = itemView.findViewById(R.id.enterChoice3);
            choice4 = itemView.findViewById(R.id.enterChoice4);

            nextQuestionBtn = itemView.findViewById(R.id.nextQuestion);
            answerSpinner = itemView.findViewById(R.id.questionAnswer);
            explanations = itemView.findViewById(R.id.answerExplanation);

            questionsType = itemView.findViewById(R.id.questionsType);
            base64Image = itemView.findViewById(R.id.base64Image);
            imageAcceptorLayout = itemView.findViewById(R.id.imageAcceptor);

        }
    }

}