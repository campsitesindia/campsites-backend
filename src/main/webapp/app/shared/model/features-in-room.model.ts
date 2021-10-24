import { IRoom } from 'app/shared/model/room.model';
import { IFeatures } from 'app/shared/model/features.model';

export interface IFeaturesInRoom {
  id?: number;
  room?: IRoom | null;
  feature?: IFeatures | null;
}

export const defaultValue: Readonly<IFeaturesInRoom> = {};
