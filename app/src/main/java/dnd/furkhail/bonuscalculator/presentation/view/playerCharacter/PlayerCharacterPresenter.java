package dnd.furkhail.bonuscalculator.presentation.view.playerCharacter;

import android.util.Log;

import javax.inject.Inject;

import dnd.furkhail.bonuscalculator.domain.business.PlayerCharacter;
import dnd.furkhail.bonuscalculator.domain.interactor.DefaultObserver;
import dnd.furkhail.bonuscalculator.domain.interactor.playerCharacter.GetPlayerCharacterUseCase;
import dnd.furkhail.bonuscalculator.domain.interactor.playerCharacter.UpdatePlayerCharacterUseCase;
import dnd.furkhail.bonuscalculator.presentation.base.Presenter;

class PlayerCharacterPresenter implements Presenter<PlayerCharacterView> {

    private static final String TAG = "PlayerCharacterPresente";

    private PlayerCharacterView mPlayerCharacterView;

    private GetPlayerCharacterUseCase mGetPlayerCharacterUseCase;
    private UpdatePlayerCharacterUseCase mUpdatePlayerCharacterUseCase;

    private boolean initializing = false;

    @Inject
    PlayerCharacterPresenter(GetPlayerCharacterUseCase getPlayerCharacterUseCase,
                             UpdatePlayerCharacterUseCase updatePlayerCharacterUseCase
                             ) {
        mGetPlayerCharacterUseCase = getPlayerCharacterUseCase;
        mUpdatePlayerCharacterUseCase = updatePlayerCharacterUseCase;
    }

    @Override
    public void resume() {
        if(!initializing){
            initialize();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mPlayerCharacterView = null;
        mGetPlayerCharacterUseCase.dispose();
        mUpdatePlayerCharacterUseCase.dispose();
    }

    @Override
    public void setView(PlayerCharacterView view) {
        mPlayerCharacterView = view;
    }

    @Override
    public void initialize() {
        Log.d(TAG, "initialize() called");
        initializing = true;
        loadPlayerCharacter();
    }

    void reloadPlayerCharacter(PlayerCharacter playerCharacter){
        hideViewRetry();
        showViewLoading();
        updatePlayerCharacter(playerCharacter);
    }

    private void loadPlayerCharacter(){
        hideViewRetry();
        showViewLoading();
        getPlayerCharacter();
    }

    private void showPlayerCharacter(PlayerCharacter playerCharacter) {
        mPlayerCharacterView.renderPlayerCharacter(playerCharacter);
    }

    private void getPlayerCharacter() {
        mGetPlayerCharacterUseCase.execute(new PlayerCharacterObserver());
    }

    private void updatePlayerCharacter(PlayerCharacter playerCharacter){
        mUpdatePlayerCharacterUseCase.execute(new PlayerCharacterObserver(), playerCharacter);
    }

    private final class PlayerCharacterObserver extends DefaultObserver<PlayerCharacter>{

        @Override
        public void onNext(PlayerCharacter playerCharacter) {
            Log.d(TAG, "onNext() called with: playerCharacter = [" + playerCharacter + "]");
            showPlayerCharacter(playerCharacter);
            initializing = false;
        }

        @Override
        public void onComplete() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable exception) {
            Log.d(TAG, "onError() called with: exception = [" + exception + "]");
            hideViewLoading();
            showViewRetry();
        }

    }

    private void showViewLoading() {
        mPlayerCharacterView.showLoading();
    }

    private void hideViewLoading() {
        mPlayerCharacterView.hideLoading();
    }

    private void showViewRetry() {
        mPlayerCharacterView.showRetry();
    }

    private void hideViewRetry() {
        mPlayerCharacterView.hideRetry();
    }
}
