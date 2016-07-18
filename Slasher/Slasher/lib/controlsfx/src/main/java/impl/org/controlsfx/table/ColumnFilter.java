/**
 * Copyright (c) 2015, ControlsFX
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of ControlsFX, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CONTROLSFX BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package impl.org.controlsfx.table;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.TableColumn;
import org.controlsfx.control.table.TableFilter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.BiPredicate;

public final class ColumnFilter<T,R> {
    private final TableFilter<T> tableFilter;
    private final TableColumn<T,R> tableColumn;

    private final ObservableList<FilterValue<T,R>> filterValues;

    private final DupeCounter<R> filterValuesDupeCounter = new DupeCounter<>(false);
    private final DupeCounter<R> visibleValuesDupeCounter = new DupeCounter<>(false);
    private final HashSet<R> unselectedValues = new HashSet<>();
    private final HashMap<CellIdentity<T>,ChangeListener<R>> trackedCells = new HashMap<>();
    
    private boolean lastFilter = false;
    private boolean isDirty = false;
    private BiPredicate<String,String> searchStrategy = (inputString, subjectString) -> subjectString.contains(inputString);
    private volatile FilterPanel filterPanel;

    private boolean initialized = false;

    public ColumnFilter(TableFilter<T> tableFilter, TableColumn<T,R> tableColumn) {
        this.tableFilter = tableFilter;
        this.tableColumn = tableColumn;

        this.filterValues = FXCollections.observableArrayList(cb -> new Observable[] { cb.selectedProperty()});
        this.attachContextMenu();
    }
    void setFilterPanel(FilterPanel filterPanel) {
        this.filterPanel = filterPanel;
    }
    FilterPanel getFilterPanel() {
        return filterPanel;
    }
    public void initialize() {
        if (!initialized) {
            initializeListeners();
            initializeValues();
            initialized = true;
        }
    }
    public boolean isInitialized() {
        return initialized;
    }

    public void selectValue(Object value) {
        filterPanel.selectValue(value);
    }
    public void unselectValue(Object value) {
        filterPanel.unSelectValue(value);
    }
    public void selectAllValues() {
        filterPanel.selectAllValues();
    }
    public void unSelectAllValues() {
        filterPanel.unSelectAllValues();
    }
    public boolean wasLastFiltered() {
        return lastFilter;
    }
    public boolean hasUnselections() {
        return unselectedValues.size() != 0;
    }
    public void setSearchStrategy(BiPredicate<String,String> searchStrategy) {
        this.searchStrategy = searchStrategy;
    }
    public BiPredicate<String,String> getSearchStrategy() {
        return searchStrategy;
    }
    public boolean isFiltered() {
        return isDirty || unselectedValues.size() > 0;
    }
    public boolean valueIsVisible(R value) {
        return visibleValuesDupeCounter.get(value) > 0;
    }
    public void applyFilter() {
    	tableFilter.executeFilter();
    	lastFilter = true;
    	tableFilter.getColumnFilters().stream().filter(c -> !c.equals(this)).forEach(c -> c.lastFilter = false);
    	tableFilter.getColumnFilters().stream().flatMap(c -> c.filterValues.stream()).forEach(FilterValue::refreshScope);
        isDirty = false;
    }

    public void resetAllFilters() { 
    	tableFilter.getColumnFilters().stream().flatMap(c -> c.filterValues.stream()).forEach(fv -> fv.selectedProperty().set(true));
    	tableFilter.resetFilter();
    	tableFilter.getColumnFilters().stream().forEach(c -> c.lastFilter = false);
    	tableFilter.getColumnFilters().stream().flatMap(c -> c.filterValues.stream()).forEach(FilterValue::refreshScope);
        isDirty = false;
    }

    public ObservableList<FilterValue<T,R>> getFilterValues() {
        return filterValues;
    }

    public TableColumn<T,?> getTableColumn() { 
        return tableColumn;
    }
    public TableFilter<T> getTableFilter() { 
        return tableFilter;
    }
    public boolean evaluate(T item) {
        ObservableValue<R> value = tableColumn.getCellObservableValue(item);

        return unselectedValues.size() == 0
                || !unselectedValues.contains(value.getValue());
    }

    private void initializeValues() {
        tableFilter.getBackingList().stream()
                .forEach(t -> addBackingItem(t, tableColumn.getCellObservableValue(t)));
        tableFilter.getTableView().getItems().stream()
                .map(tableColumn::getCellObservableValue).forEach(this::addVisibleItem);

    }
    private void addBackingItem(T item, ObservableValue<R> cellValue) {
        if (cellValue == null) {
            return;
        }
        if (filterValuesDupeCounter.add(cellValue.getValue()) == 1) {
            filterValues.add(new FilterValue<>(cellValue.getValue(),this));
        }

        //listen to cell value and track it
        CellIdentity<T> trackedCellValue = new CellIdentity<>(item);
        ChangeListener<R> changeListener = (observable, oldValue, newValue) -> {

            if (filterValuesDupeCounter.add(newValue) == 1) {
                filterValues.add(new FilterValue<>(newValue,this));
            }
            if (filterValuesDupeCounter.remove(oldValue) == 0) {
                FilterValue<T,R> existingFilterValue = filterValues.stream()
                        .filter(fv -> Optional.ofNullable(fv.getValue()).equals(Optional.ofNullable(oldValue))).findAny().get();
                filterValues.remove(existingFilterValue);
            }
        };
        cellValue.addListener(changeListener);
        trackedCells.put(trackedCellValue,changeListener);
    }
    private void removeBackingItem(T item, ObservableValue<R> cellValue) {
        if (cellValue == null) {
            return;
        }
        if (filterValuesDupeCounter.remove(cellValue.getValue()) == 0) {
            FilterValue<T,R> existingFilterValue = filterValues.stream()
                    .filter(fv -> Optional.ofNullable(fv.getValue()).equals(Optional.ofNullable(cellValue.getValue()))).findAny().get();
            filterValues.remove(existingFilterValue);
        }

        //remove listener from cell
        ChangeListener<R> listener = trackedCells.get(new CellIdentity<>(item));
        cellValue.removeListener(listener);
        trackedCells.remove(new CellIdentity<>(item));
    }
    private void addVisibleItem(ObservableValue<R>  cellValue) {
        if (cellValue != null) {
            visibleValuesDupeCounter.add(cellValue.getValue());
        }
    }
    private void removeVisibleItem(ObservableValue<R>  cellValue) {
        if (cellValue != null) {
            visibleValuesDupeCounter.remove(cellValue.getValue());
        }
    }
    private void initializeListeners() {

        //listen to backing list and update distinct values accordingly
        tableFilter.getBackingList().addListener((ListChangeListener<T>) lc -> {
            while (lc.next()) {
                if (lc.wasAdded()) {
                    lc.getAddedSubList().stream()
                            .forEach(t -> addBackingItem(t,tableColumn.getCellObservableValue(t)));
                }
                if (lc.wasRemoved()) {
                    lc.getRemoved().stream()
                            .forEach(t -> removeBackingItem(t,tableColumn.getCellObservableValue(t)));
                }
            }
        });
        //listen to visible items and update visible values accordingly
        tableFilter.getTableView().getItems().addListener((ListChangeListener<T>) lc -> {
            while (lc.next()) {
                if (lc.wasAdded()) {
                    lc.getAddedSubList().stream()
                            .map(tableColumn::getCellObservableValue)
                            .forEach(this::addVisibleItem);
                }
                if (lc.wasRemoved()) {
                    lc.getRemoved().stream()
                            .map(tableColumn::getCellObservableValue)
                            .forEach(this::removeVisibleItem);
                }
            }
        });

        //listen to selections on filterValues
        filterValues.addListener((ListChangeListener<FilterValue<T,R>>) lc -> {
            while (lc.next()) {
                if (lc.wasRemoved()) {
                    lc.getRemoved().stream()
                            .filter(v -> !v.selectedProperty().get())
                            .forEach(unselectedValues::remove);
                }
                if (lc.wasUpdated()) {
                    int from = lc.getFrom();
                    int to = lc.getTo();
                    lc.getList().subList(from,to).forEach(v -> {
                        boolean value = v.selectedProperty().getValue();
                        if (!value) {
                            isDirty = true;
                            unselectedValues.add(v.getValue());
                        }
                        else {
                            isDirty = true;
                            unselectedValues.remove(v.getValue());
                        }
                    });
                }
            }
        });
    }

    /**Leverages tableColumn's context menu to attach filter panel */
    private void attachContextMenu() {

        ContextMenu contextMenu = new ContextMenu();

        CustomMenuItem item = FilterPanel.getInMenuItem(this, contextMenu);

        contextMenu.getStyleClass().add("column-filter");
        contextMenu.getItems().add(item);

        tableColumn.setContextMenu(contextMenu);

        contextMenu.setOnShowing(ae -> initialize());
    }

    private static final class CellIdentity<T> {
        private final T item;

        CellIdentity(T item) {
            this.item = item;
        }

        @Override
        public boolean equals(Object other) {
            return this.item == ((CellIdentity<?>)other).item;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(item);
        }
    }
}
