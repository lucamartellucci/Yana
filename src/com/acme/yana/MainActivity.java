package com.acme.yana;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.acme.yana.adapter.NoteAdapter;

public class MainActivity extends Activity implements OnClickListener, OnItemLongClickListener {
	
	private Button addNoteButton;

	// linear layout objects
	private LinearLayout addNoteLinearLayout;
	private ImageButton saveNoteButton;
	private EditText noteEditText;
	
	//listview objects
	private static List<String> notes = new ArrayList<String>();
	private ArrayAdapter<String> noteAdapter;
	private ListView notesListView;
	
	private int mSelectedItemListForContextMenu;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// add a few default notes
		if (notes.size() == 0) {
			notes.addAll(Arrays.asList(getResources().getStringArray(R.array.default_notes)));
		}
		
		noteAdapter = new NoteAdapter(this, android.R.layout.simple_list_item_1, notes);
		
		notesListView = (ListView) findViewById(R.id.listview);
		notesListView.setAdapter(noteAdapter);
		notesListView.setOnItemLongClickListener(this);
		
		addNoteButton = (Button) findViewById(R.id.bt_addnote);
		addNoteButton.setOnClickListener(this);
		
		saveNoteButton = (ImageButton) findViewById(R.id.bt_save_note);
		saveNoteButton.setOnClickListener(this);
		
		addNoteLinearLayout = (LinearLayout) findViewById(R.id.ll_addnote);
		
		noteEditText = (EditText) findViewById(R.id.text_addnote);
		
		registerForContextMenu(notesListView);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.context_menu, menu);
		 menu.setHeaderTitle(getString(R.string.label_cm_menu));
		 super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_delete:
			
			notes.remove(mSelectedItemListForContextMenu);
			noteAdapter.notifyDataSetChanged();
			
			Toast.makeText(getApplicationContext(), getString(R.string.toast_info_delete_item), Toast.LENGTH_LONG).show();
			return true;
		}
		
		
		return false;
	}

	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		final String item = (String) parent.getItemAtPosition(position);
		Log.d("Yana", new StringBuilder("The selected note is: ").append(item).toString());
		mSelectedItemListForContextMenu = position;
		return false;
	}
	
	
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		
		case R.id.bt_addnote:
			
			addNoteLinearLayout.setVisibility(View.VISIBLE);
			break;

		case R.id.bt_save_note:
			
			String string = noteEditText.getText().toString();
			Log.d("Yana", new StringBuilder("The new note text is: ").append(string).toString());
			
			if (string != null && !"".equals(string)) {
				
				notes.add(string);
				noteAdapter.notifyDataSetChanged();
				addNoteLinearLayout.setVisibility(View.GONE);
				noteEditText.setText(null);
				// hide the keyboard
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(noteEditText.getWindowToken(), 0);
				
				Toast.makeText(getApplicationContext(), getString(R.string.toast_info_add_item), Toast.LENGTH_LONG).show();
				
			} else {
				Toast.makeText(getApplicationContext(), getString(R.string.toast_alert_no_text), Toast.LENGTH_LONG).show();
			}
			
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
