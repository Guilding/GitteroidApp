package com.amatkivskiy.gitteroid.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.presenter.MainActivityPresenter;
import com.amatkivskiy.gitteroid.ui.LoadingCallbacks;
import com.amatkivskiy.gitteroid.ui.adapter.ViewChatRoomAdapter;
import com.amatkivskiy.gitteroid.ui.fragment.AllRoomsFragmentDialog;
import com.amatkivskiy.gitteroid.ui.fragment.AllRoomsFragmentDialogBuilder;
import com.astuetz.PagerSlidingTabStrip;
import com.github.clans.fab.FloatingActionButton;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.octicons_typeface_library.Octicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dmax.dialog.SpotsDialog;
import nucleus.factory.RequiresPresenter;

import static com.amatkivskiy.gitteroid.presenter.MainActivityPresenter.DRAWER_ITEM_ALL_IDENTIFIER;
import static com.amatkivskiy.gitteroid.presenter.MainActivityPresenter.DRAWER_ITEM_SETTINGS_IDENTIFIER;
import static com.amatkivskiy.gitteroid.presenter.MainActivityPresenter.DRAWER_ITEM_SIGN_OUT_IDENTIFIER;
import static com.amatkivskiy.gitteroid.ui.adapter.ViewChatRoomAdapter.TabRemoveListener;

@RequiresPresenter(MainActivityPresenter.class)
@SuppressLint("Deprecation")
public class MainActivity extends com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity<MainActivityPresenter> implements
        AllRoomsFragmentDialog.RoomSelectionListener,
                                                                      ViewPager.OnPageChangeListener,
                                                                      TabRemoveListener,
        LoadingCallbacks {

  private AccountHeader header;
  private Drawer drawer;
  private ViewChatRoomAdapter viewPagerAdapter;

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.viewpager_rooms)
  ViewPager viewPager;
  @BindView(R.id.tablayout_rooms)
  PagerSlidingTabStrip tabLayout;

  private SpotsDialog dialog;

  public static Intent createIntent(Context context) {
    return new Intent(context, MainActivity.class);
  }

  @Override
  public void onRoomSelected(int position) {
    getPresenter().onRoomSelected(position);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setSupportActionBar(toolbar);
    setupDrawer(savedInstanceState);
  }

  @Override
  protected int getContentViewResource() {
    return R.layout.activity_main;
  }

  private void setupDrawer(Bundle savedInstanceState) {
    header = new AccountHeaderBuilder()
        .withActivity(this)
        .withOnlyMainProfileImageVisible(true)
        .withProfileImagesClickable(false)
        .withSelectionListEnabled(false)
        .withHeaderBackground(R.drawable.login_activity_background)
        .withSavedInstance(savedInstanceState)
        .withProfileImagesClickable(false)
        .build();

    View updatedHeaderView = setupHeaderWithSectionAndButton(header.getView());

    drawer = new DrawerBuilder()
        .withActivity(this)
        .withToolbar(toolbar)
        .withHeaderDivider(false)
        .withHeader(updatedHeaderView) //set the AccountHeader we created earlier for the header
        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
          @Override
          public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
            if (iDrawerItem != null) {
              getPresenter().onDrawerItemSelected(iDrawerItem);
            }
            return false;
          }
        })
        .withSavedInstance(savedInstanceState)
        .withShowDrawerOnFirstLaunch(true)
        .build();

//    //only set the active selection or active profile if we do not recreate the activity
    if (savedInstanceState == null) {
      // set the selection to the item with the identifier 11
//      drawer.setSelection(21, false);
    }

    setupViewPager();
  }

  private FrameLayout setupHeaderWithSectionAndButton(final View headerView) {
    View roomItem = LayoutInflater.from(this).inflate(R.layout.material_drawer_item_section, null,
                                                      false);
    TextView title = (TextView) roomItem.findViewById(R.id.material_drawer_name);
    title.setText("Rooms");

    LinearLayout headerParent = new LinearLayout(this);
    headerParent.setOrientation(LinearLayout.VERTICAL);

    headerParent.addView(headerView);
    headerParent.addView(roomItem);

    final FrameLayout headerContainer = new FrameLayout(this);
    headerContainer.addView(headerParent);

    final FloatingActionButton button = new FloatingActionButton(this);
    button.setButtonSize(FloatingActionButton.SIZE_MINI);
    button.setImageDrawable(
        new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_refresh).color(Color.WHITE));
    button.setColorNormal(Color.parseColor("#0080FF"));
    button.setColorPressed(Color.parseColor("#2E64FE"));

    headerParent.getViewTreeObserver().addOnGlobalLayoutListener(
        new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          @SuppressWarnings("deprecation")
          public void onGlobalLayout() {

            int height = headerView.getHeight();
            int width = headerView.getWidth();

            int buttonWidth = getResources().getDimensionPixelSize(R.dimen.fab_size_mini);

            int
                finalMarginTop =
                height - getResources().getDimensionPixelSize(R.dimen.fab_icon_size)
                - getResources()
                    .getDimensionPixelSize(R.dimen.fab_shadow_offset);
            int finalMarginLeft = width - buttonWidth - getResources()
                .getDimensionPixelSize(R.dimen.refresh_button_margin_right);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(finalMarginLeft, finalMarginTop, 0, 0);
            headerContainer.addView(button, layoutParams);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
              headerContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else
              headerContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);

          }
        });

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        drawer.closeDrawer();
        getPresenter().onRefreshClicked();
      }
    });

    return headerContainer;
  }

  public void showDrawerRooms(List<com.amatkivskiy.gitteroid.model.RoomModel> rooms) {
    drawer.removeAllItems();

    BadgeStyle unReadBadgeStyle = com.amatkivskiy.gitteroid.util.BadgeUtils.getUnReadBadgeStyle(this);
    BadgeStyle mentionedBadgeStyle = com.amatkivskiy.gitteroid.util.BadgeUtils.getMentionedBadgeStyle(this);

    for (com.amatkivskiy.gitteroid.model.RoomModel room : rooms) {
      PrimaryDrawerItem item = new com.amatkivskiy.gitteroid.ui.drawer.DrawerItemWithUrlIcon()
          .withName(room.name)
          .withIcon(new ImageHolder(room.roomIconUrl))
          .withDescription(room.groupName)
          .withIdentifier(getPresenter().getIdentifierForRoomPosition(rooms.indexOf(room)));

      if (room.unreadItems > 0) {
        item.withBadgeStyle(unReadBadgeStyle).withBadge(com.amatkivskiy.gitteroid.util.BadgeUtils.getUnreadItemsText(
            room.unreadItems));
      }

      if (room.mentions > 0) {
        item.withBadgeStyle(mentionedBadgeStyle).withBadge(com.amatkivskiy.gitteroid.util.BadgeUtils.getMentionedItemsText(
            room.mentions));
      }

      drawer.addItem(item);
    }

    initFunctionalDrawerItems();
  }

  private void initFunctionalDrawerItems() {
    int iconColor = Color.GRAY;

    drawer.addItem(new PrimaryDrawerItem()
                       .withName("All")
                       .withIconColor(iconColor)
                       .withIcon(GoogleMaterial.Icon.gmd_storage)
                       .withIdentifier(DRAWER_ITEM_ALL_IDENTIFIER));

    drawer.addItem(new DividerDrawerItem());

    drawer.addItem(new PrimaryDrawerItem()
                       .withName("Settings")
                       .withIcon(Octicons.Icon.oct_settings)
                       .withSelectable(false)
                       .withIdentifier(DRAWER_ITEM_SETTINGS_IDENTIFIER)
    );

    drawer.addItem(new PrimaryDrawerItem()
                       .withName("Sign out")
                       .withIcon(Octicons.Icon.oct_sign_out)
                       .withSelectable(false)
                       .withIdentifier(DRAWER_ITEM_SIGN_OUT_IDENTIFIER)
    );
  }

  private void setupViewPager() {
    viewPagerAdapter = new ViewChatRoomAdapter(this, this, viewPager);
    viewPager.setAdapter(viewPagerAdapter);
    viewPager.setOffscreenPageLimit(10);
    viewPager.addOnPageChangeListener(this);
    tabLayout.setViewPager(viewPager);
  }

  public void initDrawerWithAccount(UserAccount account) {
    header.removeProfile(0);
    header.addProfile(new ProfileDrawerItem()
                          .withEmail(account.displayName)
                          .withIcon(account.avatarUrlMedium)
                          .withName("@" + account.username)
        , 0);
  }

  public void showError(Throwable e) {
    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
  }

  public void showAllRoomsView(ArrayList<com.amatkivskiy.gitteroid.model.RoomModel> userRooms) {
    AllRoomsFragmentDialog fragment = new AllRoomsFragmentDialogBuilder(userRooms).build();
    fragment.show(getSupportFragmentManager(), "dialog");
  }

  public void showRoom(com.amatkivskiy.gitteroid.model.RoomModel selectedRoom) {
    String id = selectedRoom.id;
    if (!viewPagerAdapter.isRoomAlreadyAdded(id)) {
      viewPagerAdapter.addRoom(selectedRoom);
    }

    viewPager.setCurrentItem(viewPagerAdapter.getRoomPosition(id));
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    //add the values which need to be saved from the drawer to the bundle
    outState = drawer.saveInstanceState(outState);
    //add the values which need to be saved from the accountHeader to the bundle
    outState = header.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onBackPressed() {
    //handle the back press :D close the drawer first and if the drawer is closed close the activity
    if (drawer != null && drawer.isDrawerOpen()) {
      drawer.closeDrawer();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);

    MenuItem menuItem = menu.findItem(R.id.action_room_users);
    menuItem.setIcon(
        new IconicsDrawable(this, FontAwesome.Icon.faw_users).actionBar().color(Color.WHITE));

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_room_users) {
      getPresenter().showRoomUsersScreen();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {
    getPresenter().onRoomFragmentSelected(viewPagerAdapter.getRoom(position));
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  public void selectDrawerItem(int identifier) {
    drawer.setSelection(identifier, false);
  }

  @Override
  public void onTabRemoving(int position) {
    if (viewPagerAdapter.getCount() <= 1) {
      Toast.makeText(MainActivity.this, "Cannot remove first tab", Toast.LENGTH_LONG).show();
      return;
    }

    int nextItemToShowPosition;
    if (position == 0) {
      nextItemToShowPosition = 1;
    } else {
      nextItemToShowPosition = position - 1;
    }

    viewPager.setCurrentItem(nextItemToShowPosition);
    viewPagerAdapter.removeRoom(position);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (dialog != null) {
      dialog.dismiss();
    }
  }

  @Override
  public void onLoadingStarted() {
    dialog = new SpotsDialog(this);
    dialog.show();
  }

  @Override
  public void onLoadingFinished() {
    if (dialog != null) {
      dialog.dismiss();
    }
  }

  @Override
  public void onLoadingFailed() {
    if (dialog != null) {
      dialog.dismiss();
    }
  }
  public com.amatkivskiy.gitteroid.model.RoomModel getCurrentSelectedRoomPosition() {
    return viewPagerAdapter.getRoom(viewPager.getCurrentItem());
  }
}
