import { IExameMedico } from 'app/entities/evento/exame-medico/exame-medico.model';

export interface IFotoExameMedico {
  id?: number;
  fotoContentType?: string;
  foto?: string;
  exameMedico?: IExameMedico | null;
}

export class FotoExameMedico implements IFotoExameMedico {
  constructor(public id?: number, public fotoContentType?: string, public foto?: string, public exameMedico?: IExameMedico | null) {}
}

export function getFotoExameMedicoIdentifier(fotoExameMedico: IFotoExameMedico): number | undefined {
  return fotoExameMedico.id;
}
