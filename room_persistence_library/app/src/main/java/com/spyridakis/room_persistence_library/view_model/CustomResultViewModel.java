package com.spyridakis.room_persistence_library.view_model;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.spyridakis.room_persistence_library.database.AppDatabase;
import com.spyridakis.room_persistence_library.database.utils.DatabaseInitializer;
import com.spyridakis.room_persistence_library.dto.LoanWithUserAndBook;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomResultViewModel extends AndroidViewModel {

    private LiveData<String> mLoansResult;

    private AppDatabase mDb;

    public CustomResultViewModel(Application application) {
        super(application);
    }

    public LiveData<String> getLoansResult() {
        return mLoansResult;
    }

    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(getApplication());

        // Populate it with initial data
        DatabaseInitializer.populateAsync(mDb);

        // Receive changes
        subscribeToDbChanges();
    }

    private void subscribeToDbChanges() {
        LiveData<List<LoanWithUserAndBook>> loans
                = mDb.loanDao().findLoansByNameAfter("Mike", getYesterdayDate());

        // Instead of exposing the list of Loans, we can apply a transformation and expose Strings.
        mLoansResult = Transformations.map(loans,
                new Function<List<LoanWithUserAndBook>, String>() {
                    @Override
                    public String apply(List<LoanWithUserAndBook> loansWithUserAndBook) {
                        StringBuilder sb = new StringBuilder();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                                Locale.US);

                        for (LoanWithUserAndBook loan : loansWithUserAndBook) {
                            sb.append(String.format("%s\n  (Returned: %s)\n",
                                    loan.bookTitle,
                                    simpleDateFormat.format(loan.endTime)));
                        }
                        return sb.toString();
                    }
                });
    }

    private Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, -1);
        return calendar.getTime();
    }
}
