package fr.tvbarthel.games.chasewhisply.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import fr.tvbarthel.games.chasewhisply.R;
import fr.tvbarthel.games.chasewhisply.model.PlayerProfile;
import fr.tvbarthel.games.chasewhisply.model.mode.GameMode;
import fr.tvbarthel.games.chasewhisply.model.mode.GameModeFactory;
import fr.tvbarthel.games.chasewhisply.ui.adapter.GameModeDetailsAdapter;

public class GameModeDetailsFragment extends Fragment implements GameModeDetailsAdapter.Listener {
	public static final String TAG = "GameModeDetailsFragment_TAG";

	private Listener mListener;
	private PlayerProfile mPlayerProfile;
	private GameModeDetailsAdapter mGameModeDetailsAdapter;

	public interface Listener {
		void onGameModeDetailsRequest(GameMode gameMode);
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof GameModeDetailsFragment.Listener) {
			mListener = (GameModeDetailsFragment.Listener) activity;
			mPlayerProfile = new PlayerProfile(activity.getSharedPreferences(
					PlayerProfile.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE));
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet GameModeDetailsFragment.Listener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_mode_details, container, false);
		mGameModeDetailsAdapter = new GameModeDetailsAdapter(getActivity(), new ArrayList<GameMode>(), this, mPlayerProfile);
		((GridView) v.findViewById(R.id.mode_details_grid_view)).setAdapter(mGameModeDetailsAdapter);
		loadGameMode();
		return v;
	}

	private void loadGameMode() {
		mGameModeDetailsAdapter.clear();

		//First mode: Kill as many Ghosts as you can in 30 seconds.
		//Sprint mode
		mGameModeDetailsAdapter.add(GameModeFactory.createRemainingTimeGame(1));

		//Second mode: Kill as many Ghosts as you can in 60 seconds. (level 2)
		//intermediary mode - not used anymore ?
		//mGameModeViewAdapter.add(GameModeFactory.createRemainingTimeGame(2));

		//Third mode: Kill as many Ghosts as you can in 30 seconds. (level 3)
		//Marathon mode
		mGameModeDetailsAdapter.add(GameModeFactory.createRemainingTimeGame(3));

		//Fourth mode: survival
		mGameModeDetailsAdapter.add(GameModeFactory.createSurvivalGame(1));

		//Fifth mode: Death to the king
		mGameModeDetailsAdapter.add(GameModeFactory.createKillTheKingGame(1));

		//Sixth mode: twenty in a row
		mGameModeDetailsAdapter.add(GameModeFactory.createTwentyInARow(1));

		//Seventh mode : memorize
		mGameModeDetailsAdapter.add(GameModeFactory.createMemorize(1));

		mGameModeDetailsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onGameModeSelected(GameMode gameMode) {
		mListener.onGameModeDetailsRequest(gameMode);
	}
}
