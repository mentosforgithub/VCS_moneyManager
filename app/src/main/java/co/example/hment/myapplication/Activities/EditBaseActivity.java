package co.example.hment.myapplication.Activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import co.example.hment.myapplication.R;

public abstract class EditBaseActivity<T> extends AppCompatActivity {

    private T data;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = initializeData();
        if (id != 0) {
            setupUIForEdit(id);
        } else {
            setupUIForCreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.ic_save) {
            saveAndExit(data);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract int getLayoutId();

    protected abstract void setupUIForCreate();

    protected abstract void setupUIForEdit(@NonNull int id);

    protected abstract void saveAndExit(@Nullable T data);

    protected abstract int initializeData();
}
