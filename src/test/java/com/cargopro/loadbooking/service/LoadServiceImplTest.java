package com.cargopro.loadbooking.service;

import com.cargopro.loadbooking.dto.LoadRequest;
import com.cargopro.loadbooking.dto.LoadResponse;
import com.cargopro.loadbooking.entity.Load;
import com.cargopro.loadbooking.entity.LoadStatus;
import com.cargopro.loadbooking.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoadServiceImplTest {

    @Mock
    private LoadRepository loadRepository;

    @InjectMocks
    private LoadServiceImpl loadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLoad() {
        LoadRequest request = new LoadRequest();
        request.setShipperId("ship123");

        Load saved = new Load();
        saved.setId(UUID.randomUUID());
        saved.setStatus(LoadStatus.POSTED);

        when(loadRepository.save(any(Load.class))).thenReturn(saved);

        LoadResponse response = loadService.createLoad(request);

        assertNotNull(response);
        assertEquals(LoadStatus.POSTED, response.getStatus());
    }

    @Test
    void testGetLoadByIdSuccess() {
        UUID id = UUID.randomUUID();
        Load load = new Load();
        load.setId(id);

        when(loadRepository.findById(id)).thenReturn(Optional.of(load));

        LoadResponse result = loadService.getLoadById(id);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetLoadByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(loadRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> loadService.getLoadById(id));
    }

    @Test
    void testDeleteLoad() {
        UUID id = UUID.randomUUID();
        doNothing().when(loadRepository).deleteById(id);

        loadService.deleteLoad(id);
        verify(loadRepository, times(1)).deleteById(id);
    }
}
