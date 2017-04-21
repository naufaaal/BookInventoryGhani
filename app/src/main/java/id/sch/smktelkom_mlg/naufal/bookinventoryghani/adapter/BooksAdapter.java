package id.sch.smktelkom_mlg.naufal.bookinventoryghani.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sch.smktelkom_mlg.naufal.bookinventoryghani.R;
import id.sch.smktelkom_mlg.naufal.bookinventoryghani.model.Books;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> implements Filterable {
    private List<Books> books;
    private List<Books> booksOri;
    private Context mContext;
    private Filter filter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtBookTitle) TextView txtBookTitle;
        @BindView(R.id.txtOtherInfo) TextView txtOtherInfo;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public BooksAdapter(Context context, List<Books> bookList) {
        this.books = bookList;
        this.booksOri = bookList;
        mContext = context;
    }

    public Filter getFilter() {
        if(filter == null)
            filter = new BooksFilter();
        return filter;
    };

    private class BooksFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults result = new FilterResults();
            String substr = constraint.toString().toLowerCase();
            if(substr == null || substr.length() == 0) {
                result.values = booksOri;
                result.count = booksOri.size();
            } else {
                final ArrayList<Books> nlist = new ArrayList<Books>();
                int count = booksOri.size();

                for(int i = 0;i<count;i++) {
                    final Books book = booksOri.get(i);
                    String value = "", value2 = "";
                    value = book.getBook_title().toLowerCase();
                    value2 = book.getBook_author().toLowerCase();
                    if(value.contains(substr) || value2.contains(substr)) {
                        nlist.add(book);
                    }
                }
                result.values = nlist;
                result.count = nlist.size();
            }

            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // TODO Auto-generated method stub
            books = (List<Books>) results.values;
            notifyDataSetChanged();
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Books book = books.get(position);
        holder.txtBookTitle.setText(book.getBook_title());
        holder.txtOtherInfo.setText(book.getBook_author());
    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }
}

