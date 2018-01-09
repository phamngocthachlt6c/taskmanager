package com.ngocthach.taskmanager.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ngocthach.taskmanager.R;
import com.ngocthach.taskmanager.db.entity.BookEntity;
import com.ngocthach.taskmanager.viewmodel.BookViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ${CLASS}
 * Created by ThachPham on 09/01/2018.
 */

public class BooksListAdapter  extends RecyclerView.Adapter<BooksListAdapter.BookVH> {

    private List<BookEntity> bookEntities;
    private Context mContext;
    private BookViewModel mViewModel;

    public BooksListAdapter(Context context, BookViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;
        bookEntities = new ArrayList<>();
    }

    public void loadList(List<BookEntity> list) {
        bookEntities.clear();
        bookEntities.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public BooksListAdapter.BookVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book_item, parent, false);
        return new BooksListAdapter.BookVH(view);
    }

    @Override
    public void onBindViewHolder(BooksListAdapter.BookVH holder, int position) {
        holder.bookName.setText(bookEntities.get(position).getBookName());
        holder.author.setText(bookEntities.get(position).getAuthor());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.getMenuInflater().inflate(R.menu.asset_list_popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                mViewModel.deleteBook(bookEntities.get(position));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
//        holder.point.setText(String.valueOf(assetEntities.get(position).getValue()));
//        holder.plusBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AssetEntity entity = assetEntities.get(position);
//                entity.setValue(entity.getValue() + 1);
//                mViewModel.updateAsset(entity);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return bookEntities.size();
    }

    class BookVH extends RecyclerView.ViewHolder {

        @BindView(R.id.bookName)
        TextView bookName;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.checkDone)
        CheckBox checkDone;

        public BookVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
