import dayjs from 'dayjs';
import { IVykyUser } from 'app/shared/model/vyky-user.model';

export interface IAvatar {
  id?: number;
  mime?: string;
  imgContentType?: string;
  img?: string;
  active?: boolean | null;
  activationAt?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs;
  deletedAt?: dayjs.Dayjs | null;
  vykyUser?: IVykyUser | null;
}

export const defaultValue: Readonly<IAvatar> = {
  active: false,
};
