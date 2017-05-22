package hslu.fs17.mobpro.project.activitytracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import hslu.fs17.mobpro.project.activitytracker.model.Author;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<FunActivity> funActivities;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView mTextView;
        public CheckBox done;
        public TextView authorName;

        public ViewHolder(View v) {
            super(v);
            this.mTextView = (TextView) v.findViewById(R.id.title);
            this.done = (CheckBox) v.findViewById(R.id.checkBox);
            this.authorName = (TextView) v.findViewById(R.id.author);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<FunActivity> myDataset) {
        this.funActivities = myDataset;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO
        FunActivity fa = this.funActivities.get(position);
        Author a = fa.getTheAuthor();
        if (a == null){
            a = new Author("Dane", "Wicki");
        }

        holder.mTextView.setText(fa.getTitle());
        holder.done.setChecked(fa.getActivityDone());
        holder.authorName.setText(a.getWholeName());

    }

    public void updateFunActivityList(List<FunActivity> listFunActiv) {
        this.funActivities = listFunActiv;
        if(listFunActiv.size() > 0){
            System.out.println(listFunActiv.get(0).getTitle());
        }
        System.out.println("TEST" + listFunActiv.size());
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return funActivities.size();
    }
}