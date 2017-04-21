package id.sch.smktelkom_mlg.naufal.bookinventoryghani;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.naufal.bookinventoryghani.model.Books;
import id.sch.smktelkom_mlg.naufal.bookinventoryghani.activity.BookFormActivity;
import id.sch.smktelkom_mlg.naufal.bookinventoryghani.adapter.BooksAdapter;
import id.sch.smktelkom_mlg.naufal.bookinventoryghani.adapter.DividerDecoration;
import id.sch.smktelkom_mlg.naufal.bookinventoryghani.helper.HelperFunction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.recyclerBook)RecyclerView recyclerBook;
    @BindView(R.id.fab)FloatingActionButton btnAdd;
    private List<Books> bookList = new ArrayList<Books>();
    private BooksAdapter mAdapter;

    public int TO_FORM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Books Catalog");

        mAdapter = new BooksAdapter(this, bookList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerBook.setLayoutManager(mLayoutManager);
        recyclerBook.setItemAnimator(new DefaultItemAnimator());
        recyclerBook.addItemDecoration(new DividerDecoration(this));

        recyclerBook.setAdapter(mAdapter);
        recyclerBook.addOnItemTouchListener(new HelperFunction.RecyclerTouchListener(this, recyclerBook, new HelperFunction.ClickListener(){
            @Override
            public void onClick(View view, int position){
                //implement later
                Intent i = new Intent(MainActivity.this, BookFormActivity.class);
                i.putExtra("bookEdit", bookList.get(position));
                startActivity(i);
            }

            @Override
            public void onLongClick(View view,final int position){
                //implement later
                final Books book = bookList.get(position);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete " + book.getBook_title() + " ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO Auto-generate method stub
                                bookList.remove(book);
                                mAdapter.notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.cancel();
                            }
                        })
                        .create();
                dialog.show();
            }
        }));

        prepareBookData();

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent app = new Intent (MainActivity.this, BookFormActivity.class);
                startActivityForResult(app, TO_FORM);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == TO_FORM){
            Books bookForm = (Books) data.getExtras().getSerializable("book");
            bookList.add(bookForm);
            Toast.makeText(this, "Book " + bookForm.getBook_title() + " successfully added", Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
        }
    }

    private void prepareBookData() {
        Books book = new Books();
        bookList.add(book);

        book = new Books();
        bookList.add(book);

        book = new Books();
        bookList.add(book);

        book = new Books();
        bookList.add(book);

        mAdapter.notifyDataSetChanged();
    }
}
