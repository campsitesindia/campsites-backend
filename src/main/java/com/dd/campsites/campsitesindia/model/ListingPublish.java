package com.dd.campsites.campsitesindia.model;

import java.util.Objects;

public class ListingPublish {

    private boolean containPhotos;
    private boolean containFeatures;

    public boolean isContainPhotos() {
        return containPhotos;
    }

    public void setContainPhotos(boolean containPhotos) {
        this.containPhotos = containPhotos;
    }

    public boolean isContainFeatures() {
        return containFeatures;
    }

    public void setContainFeatures(boolean containFeatures) {
        this.containFeatures = containFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingPublish that = (ListingPublish) o;
        return containPhotos == that.containPhotos && containFeatures == that.containFeatures;
    }

    @Override
    public int hashCode() {
        return Objects.hash(containPhotos, containFeatures);
    }
}
