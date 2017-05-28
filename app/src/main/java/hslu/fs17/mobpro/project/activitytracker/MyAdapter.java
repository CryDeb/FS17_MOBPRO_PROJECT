package hslu.fs17.mobpro.project.activitytracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import hslu.fs17.mobpro.project.activitytracker.model.Author;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity;
import io.objectbox.Box;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final Context myContext;
    private List<FunActivity> funActivities;
    private Box<FunActivity> funActivityBox;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView mTextView;
        public CheckBox done;
        public TextView authorName;
        public Box<FunActivity> funActivityBox;
        public List<FunActivity> funActivities;

        public ViewHolder(View v, final List<FunActivity> pfunActivities, final Box<FunActivity> pfunActivityBox) {
            super(v);
            this.funActivityBox = pfunActivityBox;
            this.funActivities = pfunActivities;
            this.mTextView = (TextView) v.findViewById(R.id.title);
            this.authorName = (TextView) v.findViewById(R.id.author);
            this.done = (CheckBox) v.findViewById(R.id.checkBox);
            this.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    FunActivity funActivity = funActivities.get(index);
                    funActivity.setActivityDone(done.isChecked());
                    funActivityBox.put(funActivity);
                }
            });
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<FunActivity> myDataset, Context myContext, Box<FunActivity> funActivityBox) {
        this.myContext = myContext;
        this.funActivities = myDataset;
        this.funActivityBox = funActivityBox;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        final ViewHolder vh = new ViewHolder(v, funActivities, this.funActivityBox);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = vh.getAdapterPosition();
                System.out.println(index);
                FunActivity fa = funActivities.get(index);
                System.out.println(fa.getID());
                System.out.println(fa.getTitle());
                Intent i = new Intent(myContext, AddFunActivity.class);
                i.putExtra("a", "b");
                i.putExtra("edit", fa.getID());
                myContext.startActivity(i);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FunActivity fa = this.funActivities.get(position);
        Author a = fa.getTheAuthor();
        if (a == null){
            a = new Author("Dane", "Wicki2");
        }
        holder.funActivities = this.funActivities;
        holder.mTextView.setText(fa.getTitle());
        holder.done.setChecked(fa.getActivityDone());
        holder.authorName.setText(a.getWholeName());
    }

    public void updateFunActivityList(List<FunActivity> listFunActiv) {
        this.funActivities = listFunActiv;
        this.notifyDataSetChanged();
    }

    public List<FunActivity> getItemList() {return funActivities;}
    @Override
    public int getItemCount() {
        return funActivities.size();
    }
}