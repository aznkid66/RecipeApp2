public static class SearchResultsFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private static final String FORECAST_SHARE_HASHTAG = " #RecipeApp";

    private ShareActionProvider mShareActionProvider;
    private String mForecast;
    private Uri mUri;

    private static final int DETAIL_LOADER = 0;


    public SearchResultsFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        return rootView;
    }
}