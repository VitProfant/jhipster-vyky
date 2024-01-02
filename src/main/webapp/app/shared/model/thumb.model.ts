import dayjs from 'dayjs';
import { IPost } from 'app/shared/model/post.model';
import { IVykyUser } from 'app/shared/model/vyky-user.model';

export interface IThumb {
  id?: number;
  version?: number;
  up?: boolean;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs | null;
  deletedAt?: dayjs.Dayjs | null;
  post?: IPost | null;
  vykyUser?: IVykyUser | null;
}

export const defaultValue: Readonly<IThumb> = {
  up: false,
};
