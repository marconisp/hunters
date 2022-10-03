import dayjs from 'dayjs/esm';
import { IFotoExameMedico } from 'app/entities/foto/foto-exame-medico/foto-exame-medico.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface IExameMedico {
  id?: number;
  data?: dayjs.Dayjs;
  nomeMedico?: string | null;
  crmMedico?: string | null;
  resumo?: string | null;
  obs?: string | null;
  fotoExameMedicos?: IFotoExameMedico[] | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class ExameMedico implements IExameMedico {
  constructor(
    public id?: number,
    public data?: dayjs.Dayjs,
    public nomeMedico?: string | null,
    public crmMedico?: string | null,
    public resumo?: string | null,
    public obs?: string | null,
    public fotoExameMedicos?: IFotoExameMedico[] | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getExameMedicoIdentifier(exameMedico: IExameMedico): number | undefined {
  return exameMedico.id;
}
