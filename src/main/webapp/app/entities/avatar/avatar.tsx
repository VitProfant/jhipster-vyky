import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './avatar.reducer';

export const Avatar = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const avatarList = useAppSelector(state => state.avatar.entities);
  const loading = useAppSelector(state => state.avatar.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="avatar-heading" data-cy="AvatarHeading">
        <Translate contentKey="jhipsterVykyApp.avatar.home.title">Avatars</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterVykyApp.avatar.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/avatar/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterVykyApp.avatar.home.createLabel">Create new Avatar</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {avatarList && avatarList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterVykyApp.avatar.id">Id</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('mime')}>
                  <Translate contentKey="jhipsterVykyApp.avatar.mime">Mime</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('mime')} />
                </th>
                <th className="hand" onClick={sort('img')}>
                  <Translate contentKey="jhipsterVykyApp.avatar.img">Img</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('img')} />
                </th>
                <th className="hand" onClick={sort('active')}>
                  <Translate contentKey="jhipsterVykyApp.avatar.active">Active</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('active')} />
                </th>
                <th className="hand" onClick={sort('activationAt')}>
                  <Translate contentKey="jhipsterVykyApp.avatar.activationAt">Activation At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('activationAt')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="jhipsterVykyApp.avatar.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('deletedAt')}>
                  <Translate contentKey="jhipsterVykyApp.avatar.deletedAt">Deleted At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deletedAt')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterVykyApp.avatar.vykyUser">Vyky User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {avatarList.map((avatar, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/avatar/${avatar.id}`} color="link" size="sm">
                      {avatar.id}
                    </Button>
                  </td>
                  <td>{avatar.mime}</td>
                  <td>
                    {avatar.img ? (
                      <div>
                        {avatar.imgContentType ? (
                          <a onClick={openFile(avatar.imgContentType, avatar.img)}>
                            <img src={`data:${avatar.imgContentType};base64,${avatar.img}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {avatar.imgContentType}, {byteSize(avatar.img)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{avatar.active ? 'true' : 'false'}</td>
                  <td>{avatar.activationAt ? <TextFormat type="date" value={avatar.activationAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{avatar.createdAt ? <TextFormat type="date" value={avatar.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{avatar.deletedAt ? <TextFormat type="date" value={avatar.deletedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{avatar.vykyUser ? <Link to={`/vyky-user/${avatar.vykyUser.id}`}>{avatar.vykyUser.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/avatar/${avatar.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/avatar/${avatar.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/avatar/${avatar.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterVykyApp.avatar.home.notFound">No Avatars found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Avatar;
