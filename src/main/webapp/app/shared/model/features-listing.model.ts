import { IListing } from 'app/shared/model/listing.model';
import { IFeatures } from 'app/shared/model/features.model';

export interface IFeaturesListing {
  id?: number;
  listing?: IListing | null;
  feature?: IFeatures | null;
}

export const defaultValue: Readonly<IFeaturesListing> = {};
