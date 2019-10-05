package com.sticket.app.sticket.activities.store.store_home;

import android.support.v4.app.Fragment;

public class StoreHomeHomeFragment extends Fragment {
//
//    private FragmentStoreStartBinding binding;
//    private List<Asset> todayAssets = new ArrayList<>();
//    private List<Asset> popularAssets = new ArrayList<>();
//    private List<Asset> newAssets = new ArrayList<>();
//    private StoreHomeHomeAssetsAdapter todayAssetsAdapter;
//    private StoreHomeHomeAssetsAdapter popularAssetsAdapter;
//    private StoreHomeHomeAssetsAdapter newAssetsAdapter;
//    private int nextTodayAssetsPage = 1;
//    private int nextPopularAssetsPage = 1;
//    private int nextNewAssetsPage = 1;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        todayAssetsAdapter = new StoreHomeHomeAssetsAdapter(todayAssets);
//        popularAssetsAdapter = new StoreHomeHomeAssetsAdapter(popularAssets);
//        newAssetsAdapter = new StoreHomeHomeAssetsAdapter(newAssets);
//
//        getTodayAssets(1);
//        getPopularAssets(1);
//        getNewAssets(1);
//
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = binding = DataBindingUtil.inflate(
//                inflater, R.layout.fragment_store_start, container, false);
//
//        // Set RecyclerView to ItemAdapter
//        //TODO: 오른쪽 끝까지 가면 새로운 페이지 가져오는 기능 구현
//
//        binding.todayItemRecycler.setAdapter(todayAssetsAdapter);
//        binding.popularItemRecycler.setAdapter(popularAssetsAdapter);
//        binding.newItemRecycler.setAdapter(newAssetsAdapter);
//
//        return binding.getRoot();
//    }
//
//    private void getTodayAssets(int page) {
//        ApiClient.getInstance()
//                .getAssetService()
//                .getTodayAssets(page)
//                .enqueue(new CustomCallback<List<Asset>>() {
//                    @Override
//                    public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
//                        if (response.body() == null) {
//                            Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
//                        } else {
//                            todayAssets.addAll(response.body());
//                            todayAssetsAdapter.notifyDataSetChanged();
//                            nextTodayAssetsPage++;
//                        }
//                    }
//                });
//    }
//
//    private void getPopularAssets(int page) {
//        ApiClient.getInstance()
//                .getAssetService()
//                .getPopularAssets(page)
//                .enqueue(new CustomCallback<List<Asset>>() {
//                    @Override
//                    public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
//                        if (response.body() == null) {
//                            Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
//                        } else {
//                            popularAssets.addAll(response.body());
//                            popularAssetsAdapter.notifyDataSetChanged();
//                            nextPopularAssetsPage++;
//                        }
//                    }
//                });
//    }
//
//    private void getNewAssets(int page) {
//        ApiClient.getInstance()
//                .getAssetService()
//                .getNewAssets(page)
//                .enqueue(new CustomCallback<List<Asset>>() {
//                    @Override
//                    public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
//                        if (response.body() == null) {
//                            Log.e("Retrofit2 Callback", String.format("onFailure: %s\n", call.request().url()));
//                        } else {
//                            newAssets.addAll(response.body());
//                            newAssetsAdapter.notifyDataSetChanged();
//                            nextNewAssetsPage++;
//                        }
//                    }
//                });
//    }
}
