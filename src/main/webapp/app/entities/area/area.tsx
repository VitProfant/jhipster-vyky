import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './area.reducer';

export const Area = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const areaList = useAppSelector(state => state.area.entities);
  const loading = useAppSelector(state => state.area.loading);

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
      <h2 id="area-heading" data-cy="AreaHeading">
        <Translate contentKey="jhipsterVykyApp.area.home.title">Areas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterVykyApp.area.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/area/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterVykyApp.area.home.createLabel">Create new Area</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {areaList && areaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterVykyApp.area.id">Id</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('version')}>
                  <Translate contentKey="jhipsterVykyApp.area.version">Version</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('version')} />
                </th>
                <th className="hand" onClick={sort('level')}>
                  <Translate contentKey="jhipsterVykyApp.area.level">Level</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('level')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="jhipsterVykyApp.area.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('minimalRole')}>
                  <Translate contentKey="jhipsterVykyApp.area.minimalRole">Minimal Role</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('minimalRole')} />
                </th>
                <th className="hand" onClick={sort('parentId')}>
                  <Translate contentKey="jhipsterVykyApp.area.parentId">Parent Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('parentId')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="jhipsterVykyApp.area.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="jhipsterVykyApp.area.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th className="hand" onClick={sort('deletedAt')}>
                  <Translate contentKey="jhipsterVykyApp.area.deletedAt">Deleted At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deletedAt')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterVykyApp.area.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {areaList.map((area, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/area/${area.id}`} color="link" size="sm">
                      {area.id}
                    </Button>
                  </td>
                  <td>{area.version}</td>
                  <td>{area.level}</td>
                  <td>{area.name}</td>
                  <td>
                    <Translate contentKey={`jhipsterVykyApp.Role.${area.minimalRole}`} />
                  </td>
                  <td>{area.parentId}</td>
                  <td>{area.createdAt ? <TextFormat type="date" value={area.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{area.updatedAt ? <TextFormat type="date" value={area.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{area.deletedAt ? <TextFormat type="date" value={area.deletedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{area.parent ? <Link to={`/area/${area.parent.id}`}>{area.parent.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/area/${area.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/area/${area.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/area/${area.id}/delete`)}
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
              <Translate contentKey="jhipsterVykyApp.area.home.notFound">No Areas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Area;
