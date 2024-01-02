import dayjs from 'dayjs';
import { ConfigItem } from 'app/shared/model/enumerations/config-item.model';

export interface IConfig {
  id?: number;
  configItem?: keyof typeof ConfigItem;
  version?: number;
  value?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs | null;
  deletedAt?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IConfig> = {};
