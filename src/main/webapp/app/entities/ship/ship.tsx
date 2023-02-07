import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IShip } from 'app/shared/model/ship.model';
import { getEntities } from './ship.reducer';

export const Ship = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const shipList = useAppSelector(state => state.ship.entities);
  const loading = useAppSelector(state => state.ship.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="ship-heading" data-cy="ShipHeading">
        Ships
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/ship/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Ship
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {shipList && shipList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Classification Society</th>
                <th>Ice Class Polar Code</th>
                <th>Technical Efficiency Code</th>
                <th>Ship Type</th>
                <th>Owner Country</th>
                <th>Flag</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {shipList.map((ship, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ship/${ship.id}`} color="link" size="sm">
                      {ship.id}
                    </Button>
                  </td>
                  <td>{ship.name}</td>
                  <td>{ship.classificationSociety}</td>
                  <td>{ship.iceClassPolarCode}</td>
                  <td>{ship.technicalEfficiencyCode}</td>
                  <td>{ship.shipType}</td>
                  <td>{ship.ownerCountry ? <Link to={`/country/${ship.ownerCountry.id}`}>{ship.ownerCountry.id}</Link> : ''}</td>
                  <td>{ship.flag ? <Link to={`/flag/${ship.flag.id}`}>{ship.flag.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ship/${ship.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/ship/${ship.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/ship/${ship.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Ships found</div>
        )}
      </div>
    </div>
  );
};

export default Ship;
