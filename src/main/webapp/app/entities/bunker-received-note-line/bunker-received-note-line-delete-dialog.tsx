import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './bunker-received-note-line.reducer';

export const BunkerReceivedNoteLineDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const bunkerReceivedNoteLineEntity = useAppSelector(state => state.bunkerReceivedNoteLine.entity);
  const updateSuccess = useAppSelector(state => state.bunkerReceivedNoteLine.updateSuccess);

  const handleClose = () => {
    navigate('/bunker-received-note-line');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(bunkerReceivedNoteLineEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="bunkerReceivedNoteLineDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="jhipsterSampleApplicationApp.bunkerReceivedNoteLine.delete.question">
        Are you sure you want to delete Bunker Received Note Line {bunkerReceivedNoteLineEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button id="jhi-confirm-delete-bunkerReceivedNoteLine" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default BunkerReceivedNoteLineDeleteDialog;
