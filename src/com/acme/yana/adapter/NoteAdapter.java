package com.acme.yana.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;


public class NoteAdapter extends ArrayAdapter<String> {

	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	public NoteAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		for (int i = 0; i < objects.size(); ++i) {
			mIdMap.put(objects.get(i), i);
		}
	}

	@Override
	public long getItemId(int position) {
		String item = getItem(position);
		Integer id = mIdMap.get(item);
		if(id == null) {
			id = 0;
		}
		return id;
	} 

	@Override
	public boolean hasStableIds() {
		return true;
	}

}
