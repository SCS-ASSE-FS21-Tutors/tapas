package ch.unisg.tapasexecutorpool.pool.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CanExecuteDto {

    boolean executable;
}
