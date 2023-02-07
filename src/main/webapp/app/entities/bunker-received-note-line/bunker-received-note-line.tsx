import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBunkerReceivedNoteLine } from 'app/shared/model/bunker-received-note-line.model';
import { getEntities } from './bunker-received-note-line.reducer';

export const BunkerReceivedNoteLine = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const bunkerReceivedNoteLineList = useAppSelector(state => state.bunkerReceivedNoteLine.entities);
  const loading = useAppSelector(state => state.bunkerReceivedNoteLine.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="bunker-received-note-line-heading" data-cy="BunkerReceivedNoteLineHeading">
        Bunker Received Note Lines
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/bunker-received-note-line/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Bunker Received Note Line
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bunkerReceivedNoteLineList && bunkerReceivedNoteLineList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Quantity</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bunkerReceivedNoteLineList.map((bunkerReceivedNoteLine, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bunker-received-note-line/${bunkerReceivedNoteLine.id}`} color="link" size="sm">
                      {bunkerReceivedNoteLine.id}
                    </Button>
                  </td>
                  <td>{bunkerReceivedNoteLine.quantity}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/bunker-received-note-line/${bunkerReceivedNoteLine.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/bunker-received-note-line/${bunkerReceivedNoteLine.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/bunker-received-note-line/${bunkerReceivedNoteLine.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Bunker Received Note Lines found</div>
        )}
      </div>
    </div>
  );
};

export default BunkerReceivedNoteLine;
