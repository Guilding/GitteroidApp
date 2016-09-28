package com.amatkivskiy.gitteroid.ui.drawer;

import android.view.View;
import android.widget.ImageView;

import com.amatkivskiy.gitteroid.R;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.OnPostBindViewListener;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

public class DrawerItemWithUrlIcon extends PrimaryDrawerItem {

  public DrawerItemWithUrlIcon() {
    super();
    mOnPostBindViewListener = new OnPostBindViewListener() {
      @Override
      public void onBindView(IDrawerItem drawerItem, View view) {
        ImageView icon = (ImageView) view.findViewById(R.id.material_drawer_icon);
        DrawerImageLoader.getInstance().cancelImage(icon);

        DrawerImageLoader.getInstance().getImageLoader().set(icon, getIcon().getUri(), null);
        icon.setVisibility(View.VISIBLE);
      }
    };
  }
}
